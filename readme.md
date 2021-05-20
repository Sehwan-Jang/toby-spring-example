# 스터디 4일차 (3장 - 템플릿)

### OCP → 변하지 않는 부분(변화에는 막혀있다) + 변하는 부분(확장)

> **템플릿이란** 바뀌는 성질이 다른 코드 중에서 변경이 거의 일어나지 않으며, 일정한 패턴으로 유지되는 특성을 가진 부분을 자유롭게 변경되는 성질을 가진 부분으로부터 독립시키는 것이다.

# 1. 다시보는 초난감 DAO

## SQLException 처리 (try-catch-finally)

→  **SQLException을 제대로 처리해주지 않으면 에러가 발생했을때, Connection, ps, rs 등등이 제대로 close 되지 않을 수 있고,  그러다 보면 서버에 커넥션에 대한 리소스가 넘쳐 서버가 다운될 수도 있다.
(보통은 close 할때 커넥션 풀에 반환해서 다시 재사용하도록 돌려놓는다.)**

### 그러나, try-catch-finally 방식으로 예외처리를 하다보면 작성하기도 짜증나고, 가독성도 좋지 않으며 테스트를 작성하기도 어렵다.

---

# 2. 변하는 것과 변하지 않는 것 (리팩토링)

## 분리와 재사용을 위한 디자인패턴 적용 (전략패턴)

분리 전의 deleteAll() 코드

```java
public void deleteAll() throws SQLException, ClassNotFoundException {
    Connection c = null;
    PreparedStatement ps = null;
    try {
        c = getConnection();
        ps = c.prepareStatement("delete from users");
        ps.executeUpdate();
    } catch (SQLException e) {
        throw e;
    } finally {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
            }
        }
        if (c != null) {
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }
}
```

변하지 않는 부분(컨텍스트)

- DB 커넥션 가져오기
- ps 실행
- 예외 발생 시 메소드 밖으로 throw
- ps, c close 하기

변하는 부분 (분리의 대상)

- ps 초기화

→ 이 부분을 전략패턴을 적용해서 리팩토링한 코드

```java
public void deleteAll() throws SQLException {
    StatementStrategy st = new DeleteAllStatement();
    jdbcContextWithStatementStrategy(st);
}

// 모든 Dao 메소드들들이 재사용할 컨텍스트
private void jdbcContextWithStatementStrategy(StatementStrategy st) throws SQLException {
    Connection c = null;
    PreparedStatement ps = null;

    try {
        c = getConnection();
        ps = st.makePreparedStatement(c);

		...
}
```

이렇게 리팩토링을 하고나면, 변하는 부분을 deleteAll 메소드(클라이언트)가 안에서 스스로 생성하여 컨텍스트로 주입하여 주기때문에,  하나의 컨텍스트(변하지 않는 로직 흐름)를 가지고 여러 DAO 메소드들이 공유할 수 있다.

비록 클라이언트와 컨텍스트를 클래스레벨로 분리하지는 않았지만, (모두  UserDao 내) 메소드 단으로 나눴다. 이것을 **마이크로 DI** 라고 한다.

## 전략과 클라이언트의 동거

### 아직 해결되지 않은 문제점

- 새로운 메소드 마다 StrategyStatement 구현 클래스 생성해야함.
    - 런타임시에 DI를 해준다는 점 뺴면, 템플릿 메소드 패턴보다 나을 것이 없음.
- PreparedStatement를 만드는데 사용할 부가정보가 있다면 Dao 메소드 내에서 만들어서 넣어줘야함.

### 해결책 : 로컬 클래스(메소드 내에 선언하는 내부클래스)

장점

- 메소드 내부에 선언되기 떄문에, 부가정보가 필요한 경우에도 생성자를 통해 받을 필요가 없고, 메소드의 파라미터나 로컬변수에도 바로 접근 가능
- 굳이 외부에 클래스파일을 하나 더 생성할 필요가 없음.

```java
public void add(final User user) throws SQLException{
    class AddStatement implements StatementStrategy{

        @Override
        public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
            PreparedStatement ps =  connection.prepareStatement(
                    "insert into users(id, name, password) values(?, ?, ?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            return ps;
        }
    }

    StatementStrategy st = new AddStatement();
    jdbcContextWithStatementStrategy(st);
}
```

여기에 익명 내부 클래스를 사용하게 되면 로컬 클래스의 장점은 그대로 가져가면서 코드도 간결해지고, 람다까지 사용가능 하다.

```java
public void add(final User user) throws SQLException{
    jdbcContextWithStatementStrategy(connection -> {
        PreparedStatement ps =  connection.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        return ps;
    });
}

public void deleteAll() throws SQLException {
    jdbcContextWithStatementStrategy(connection -> 
				connection.prepareStatement("delete from users"));
}
```
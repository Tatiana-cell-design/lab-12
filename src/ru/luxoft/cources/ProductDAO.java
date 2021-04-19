package ru.luxoft.cources;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    //  public static void main(String[] argv) throws SQLException {
    public static final String DB_DRIVER = "org.h2.Driver";
    public static final String DB_CONNECTION_URL = "jdbc:h2:D:\\Luxoft\\H2_DB\\test";
    public static final String DB_USER = "sa";
    public static final String DB_PASSWORD = "";


    private Connection getConnectionTest() throws SQLException {
           System.out.println("-------- H2 JDBC Connection Testing ------------");

            try {

          Class.forName(DB_DRIVER);

            } catch (ClassNotFoundException e) {

          System.out.println("Where is your H2 JDBC Driver? "
                  + "Include in your library path!");
          e.printStackTrace();

              }

             System.out.println("H2 JDBC Driver Registered!");
            return DriverManager.getConnection(
               DB_CONNECTION_URL,
               DB_USER,
               DB_PASSWORD);
         }

    public List<Product> getProductById(int id) throws Exception {
        List<Product> products = new ArrayList<Product>();
        // Получение соединения с БД
        Connection con = getConnectionTest();

        // Подготовка SQL-запроса
        PreparedStatement st = con.prepareStatement(
                "Select description, rate, quantity " +
                        "From products " +
                        "Where id = ?");

        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        Product product = null;

        while (rs.next()) {
            // Из каждой строки выборки выбираем результаты,
            // формируем новый объект Product
            // и помещаем его в коллекцию
            product = new Product(
                    id,
                    rs.getString(1),
                    rs.getFloat(2),
                    rs.getInt(3));
            products.add(product);
        }
        // Закрываем выборку и соединение с БД
        rs.close();
        con.close();
        return products;
    }


    public void addProduct(Product product) throws Exception {
        // Получение соединения с БД
        Connection con = getConnectionTest();

        // Подготовка SQL-запроса
        PreparedStatement st = con.prepareStatement(
                "Insert into products " +
                        "(id, description, rate, quantity) " +
                        "values (?, ?, ?, ?)");
        // Указание значений параметров запроса
        st.setInt(1, product.getId());
        st.setString(2, product.getDescription());
        st.setFloat(3, product.getRate());
        st.setInt(4, product.getQuantity());

        // Выполнение запроса
        st.executeUpdate();

        con.close();
    }

    public void setProduct(Product product) throws Exception {
        // Получение соединения с БД
        Connection con = getConnectionTest();

        // Подготовка SQL-запроса
        PreparedStatement st = con.prepareStatement(
                "Update products " +
                        "Set description=?, rate=?, quantity=? " +
                        "Where id=?");
        // Указание значений параметров запроса
        st.setString(1, product.getDescription());
        st.setFloat(2, product.getRate());
        st.setInt(3, product.getQuantity());
        st.setInt(4, product.getId());

        // Выполнение запроса
        st.executeUpdate();
        con.close();
    }

    public void removeProduct(int id) throws Exception {
        // Получение соединения с БД
        Connection con = getConnectionTest();

        // Подготовка SQL-запроса
        PreparedStatement st = con.prepareStatement(
                "Delete from products " +
                        "Where id = ?");
        // Указание значений параметров запроса
        st.setInt(1, id);

        // Выполнение запроса
        st.executeUpdate();
        con.close();
    }
}
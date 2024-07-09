package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Connected to the database!");

            // 插入示例数据
            insertSampleData(connection);

            // 主菜单
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("请选择操作：");
                System.out.println("1. 查询所有村民");
                System.out.println("2. 添加新村民");
                System.out.println("3. 更新村民信息");
                System.out.println("4. 删除村民信息");
                System.out.println("5. 退出");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                switch (choice) {
                    case 1:
                        queryAllVillagers(connection);
                        break;
                    case 2:
                        inputVillagerInfo(connection);
                        break;
                    case 3:
                        updateVillagerInfo(connection);
                        break;
                    case 4:
                        deleteVillagerInfo(connection);
                        break;
                    case 5:
                        System.out.println("退出程序");
                        return;
                    default:
                        System.out.println("无效的选择，请重新选择");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertSampleData(Connection connection) {
        String[] insertStatements = {
                "INSERT INTO villagers (name, age, occupation) VALUES ('John Doe', 30, 'Farmer')",
                "INSERT INTO villagers (name, age, occupation) VALUES ('Jane Smith', 25, 'Teacher')",
                "INSERT INTO villagers (name, age, occupation) VALUES ('Emily Johnson', 40, 'Nurse')"
        };
        try (Statement stmt = connection.createStatement()) {
            for (String sql : insertStatements) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void inputVillagerInfo(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();

        scanner.nextLine(); // consume the newline character

        System.out.print("Enter occupation: ");
        String occupation = scanner.nextLine();

        String insertSQL = String.format("INSERT INTO villagers (name, age, occupation) VALUES ('%s', %d, '%s')",
                name, age, occupation);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(insertSQL);
            System.out.println("Villager information added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateVillagerInfo(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of the villager to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        System.out.print("Enter new occupation: ");
        String occupation = scanner.nextLine();

        String updateSQL = String.format("UPDATE villagers SET name='%s', age=%d, occupation='%s' WHERE id=%d",
                name, age, occupation, id);
        try (Statement stmt = connection.createStatement()) {
            int rowsAffected = stmt.executeUpdate(updateSQL);
            if (rowsAffected > 0) {
                System.out.println("Villager information updated successfully.");
            } else {
                System.out.println("Villager with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteVillagerInfo(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of the villager to delete: ");
        int id = scanner.nextInt();

        String deleteSQL = String.format("DELETE FROM villagers WHERE id=%d", id);
        try (Statement stmt = connection.createStatement()) {
            int rowsAffected = stmt.executeUpdate(deleteSQL);
            if (rowsAffected > 0) {
                System.out.println("Villager information deleted successfully.");
            } else {
                System.out.println("Villager with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void queryAllVillagers(Connection connection) {
        String query = "SELECT * FROM villagers";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Occupation: " + rs.getString("occupation"));
                System.out.println("------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

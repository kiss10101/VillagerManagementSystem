package org.example.service;

import org.example.database.DatabaseConnection;
import org.example.util.InputUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VillagerService {

    public void insertSampleData(Connection connection) {
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

    public void addVillager(Connection connection) {
        while (true) {
            String name = InputUtil.getString("Enter name: ");
            int age = InputUtil.getInt("Enter age: ");
            InputUtil.getString(""); // consume the newline character
            String occupation = InputUtil.getString("Enter occupation: ");

            String insertSQL = String.format("INSERT INTO villagers (name, age, occupation) VALUES ('%s', %d, '%s')",
                    name, age, occupation);
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(insertSQL);
                System.out.println("Villager information added successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.print("Do you want to add another villager? (y/n): ");
            if (!InputUtil.getString("").equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    public void updateVillager(Connection connection) {
        while (true) {
            int id = InputUtil.getInt("Enter ID of the villager to update: ");
            InputUtil.getString(""); // consume the newline character
            String name = InputUtil.getString("Enter new name: ");
            int age = InputUtil.getInt("Enter new age: ");
            InputUtil.getString(""); // consume the newline character
            String occupation = InputUtil.getString("Enter new occupation: ");

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

            System.out.print("Do you want to update another villager? (y/n): ");
            if (!InputUtil.getString("").equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    public void deleteVillager(Connection connection) {
        while (true) {
            int id = InputUtil.getInt("Enter ID of the villager to delete: ");
            InputUtil.getString(""); // consume the newline character

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

            System.out.print("Do you want to delete another villager? (y/n): ");
            if (!InputUtil.getString("").equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    public void queryAllVillagers(Connection connection) {
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
        InputUtil.waitForEnter();
    }
}

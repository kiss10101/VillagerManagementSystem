package org.example;

import org.example.database.DatabaseConnection;
import org.example.service.VillagerService;
import org.example.util.InputUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        VillagerService villagerService = new VillagerService();

        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Connected to the database!");

            // 插入示例数据
            villagerService.insertSampleData(connection);

            // 主菜单
            while (true) {
                System.out.println("请选择操作：");
                System.out.println("1. 查询所有村民");
                System.out.println("2. 添加新村民");
                System.out.println("3. 更新村民信息");
                System.out.println("4. 删除村民信息");
                System.out.println("5. 退出");

                int choice = InputUtil.getInt("选择: ");
                InputUtil.getString(""); // consume the newline character

                switch (choice) {
                    case 1:
                        villagerService.queryAllVillagers(connection);
                        break;
                    case 2:
                        villagerService.addVillager(connection);
                        break;
                    case 3:
                        villagerService.updateVillager(connection);
                        break;
                    case 4:
                        villagerService.deleteVillager(connection);
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
}

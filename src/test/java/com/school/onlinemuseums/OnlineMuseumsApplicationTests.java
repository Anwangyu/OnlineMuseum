package com.school.onlinemuseums;

import com.school.onlinemuseums.domain.entity.User_basic;
import com.school.onlinemuseums.mapper.User_BasicMapper;
import com.school.onlinemuseums.server.FileStorage_Service;
import com.school.onlinemuseums.server.Student_Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class  OnlineMuseumsApplicationTests {

	@Autowired
	private User_BasicMapper userBasicMapper;
	@Autowired
	private Student_Service studentService;



	// 测试查询
	@Test
	void testSelectByUser() {
		String username = "2";
		String password = "1";
		int id = 1;

		User_basic name = userBasicMapper.usernameVerification(username);
		System.out.println(username);
		if (name != null) {
			System.out.println("查询结果：");
			System.out.println("学号：" + name.getStudentId());
			System.out.println("用户名：" + name.getStudentUsername());
			System.out.println("密码：" + name.getStudentPassword());
		} else {
			System.out.println("查询失败：用户不存在或用户名密码学号不匹配");
		}
	}
	// redis服务测试连接
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	void contextLoads() {
		//向redis中添加数据
		redisTemplate.opsForValue().set("keys", "value值");
		//根据键值取出数据
		System.out.println(redisTemplate.opsForValue().get("keys"));
	}

	// 测试删除
	@Test
	void testDeleteUser() {
		String studentUsername = "testuser";
		Long studentId = 1234567L;
		String studentPassword = "testpassword";

		User_basic user = new User_basic();
		user.setStudentUsername(studentUsername);
		user.setStudentId(studentId);
		user.setStudentPassword(studentPassword);
		userBasicMapper.register(user);

		int rowsAffected = userBasicMapper.deleteUser(studentId);

		if (rowsAffected > 0) {
			System.out.println("成功删除用户信息");
		} else {
			System.out.println("删除用户信息失败");
		}
	}
	//测试修改
	@Test
	void testChangePassword() {
		// 准备测试数据
		Long studentId = 1234567L;
		String newPassword = "newsssPassword";

		// 调用修改密码方法
		int result = userBasicMapper.updatePassword(studentId, newPassword, newPassword);

		// 验证修改密码操作的返回结果
		assertEquals(1, result, "修改密码操作应返回1");
	}

	@Autowired
	private FileStorage_Service fileStorageService;

	@Test
	public void testStoreFile() {
		try {
			// 准备测试文件
			String fileName = "test-file.txt";
			String contentType = "text/plain";
			String content = "This is a test file content";
			InputStream inputStream = new FileInputStream("test-file.txt");

			MockMultipartFile file = new MockMultipartFile(fileName, fileName, contentType, inputStream);

			// 调用文件存储服务
			String storedFileName = fileStorageService.storeFile(file);

			// 验证文件是否成功存储
			assertTrue(storedFileName != null && !storedFileName.isEmpty());

			// 如果需要，可以验证文件是否存在于文件系统中
			// 例如，你可以检查文件是否存在于预期的目录中

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testGetAllUsers() {
		// 调用获取所有用户的方法
		List<User_basic> userList = userBasicMapper.getAllUsers();
		System.out.println(userList);
		// 打印获取到的用户列表信息
		if (userList != null && !userList.isEmpty()) {
			for (User_basic user : userList) {
				System.out.println("学号：" + user.getStudentId());
				System.out.println("用户名：" + user.getStudentUsername());
				System.out.println("密码：" + user.getStudentPassword());
			}
		} else {
			System.out.println("用户列表为空");
		}

		// 验证用户列表不为空
		assertNotNull(userList, "用户列表不应为空");
		assertFalse(userList.isEmpty(), "用户列表不应为空");
	}

}

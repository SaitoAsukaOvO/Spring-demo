package com.dyh.mapper;

import com.dyh.pojo.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class UserMapperImpl implements UserMapper {
    private SqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<User> selectUser() {
        User user = new User(5, "wang", "111");
        UserMapper mapper = sqlSessionTemplate.getMapper(UserMapper.class);
        mapper.addUser(user);
        mapper.deleteUser(5);
        return mapper.selectUser();
    }

    public int addUser(User user) {
        return sqlSessionTemplate.getMapper(UserMapper.class).addUser(user);
    }

    public int deleteUser(int id) {
        return sqlSessionTemplate.getMapper(UserMapper.class).deleteUser(id);
    }
}

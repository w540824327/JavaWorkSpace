package test;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
@Select("select * from abc where ID=#{id}")
User findById(int id);

@Insert("insert into abc(name,age) values(#{name},#{age})")
@Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
void insert(User user);
}

package com.zhugw;

import lombok.Data;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;

/**
 * Created by duanmu on 9/25/16.
 */
@State(Scope.Thread)
public class CopyPropertiesBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CopyPropertiesBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .jvmArgs("-server")
                .resultFormat(ResultFormatType.TEXT)
                .build();

        new Runner(opt).run();
    }

    private UserModel model = new UserModel();
    private BeanCopier beanCopier = BeanCopier.create(UserModel.class, UserVo.class, false);

    /**
     * 人工setter复制属性
     *
     * @return
     */
    @Benchmark
    public UserVo manuallySetter() {
        UserVo vo = new UserVo();
        vo.setAvatar(model.getAvatar());
        vo.setNick(model.getNick());
        vo.setCreateTime(model.getCreateTime());
        vo.setId(model.getId());
        vo.setLevel(model.getLevel());
        vo.setAvatar(model.getAvatar());
        vo.setNick(model.getNick());
        vo.setCreateTime(model.getCreateTime());
        vo.setId(model.getId());
        vo.setLevel(model.getLevel());

        return vo;
    }

    @Benchmark
    public UserVo beanUitls() {
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(this.model, vo);
        return vo;
    }

    @Benchmark
    public UserVo beanCopier() {
        UserVo vo = new UserVo();
        beanCopier.copy(this.model, vo, null);
        return vo;
    }

    @Data
    static class UserModel {
        int id = 1;
        String nick = "foo";
        String phone = "1234567890";
        String password = "123456";
        String avatar = "my.png";
        int status = 1;
        int level = 5;
        Date createTime = new Date();
    }

    @Data
    static class UserVo {
        int id;
        String nick;
        String phone;
        String password;
        String avatar;
        int status;
        int level;
        Date createTime;
    }
}

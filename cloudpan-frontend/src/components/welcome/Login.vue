<script setup>
import {User, Lock} from '@element-plus/icons-vue'
import {reactive} from "vue";
import {ElMessage} from "element-plus";
import {get, post} from "@/net";
import router from "@/router";
import {useStore} from "@/stores/counter";

const form = reactive({
    username: '',
    password: '',
    remember: false
})
const store = useStore()


const login = () =>{
    if(!form.username || !form.password){
        ElMessage.warning('请填写用户名和密码！')
    } else {
        post('/api/auth/login',{
            username: form.username,
            password: form.password,
            remember: form.remember
        },(message) => {
            ElMessage.success(message)
            router.push('/index')
        })
    }
}
</script>

<template>
    <div class="content">
        <el-form >
            <el-form-item prop="username">
                <el-input
                        v-model="form.username"
                        placeholder="用户名"
                        type="text"
                        tabindex="1"
                        :prefix-icon="User"
                        size="large"
                />
            </el-form-item>
            <el-form-item prop="password">
                <el-input
                        v-model="form.password"
                        placeholder="密码"
                        type="password"
                        tabindex="2"
                        :prefix-icon="Lock"
                        size="large"
                        show-password
                />
            </el-form-item>
            <el-row style="margin-top: 5px;">
                <el-col :span="12" style="text-align: left">
                    <el-checkbox  v-model="form.remember" label="记住我"/>
                </el-col>
                <el-col :span="12" style="text-align: right">
                    <el-link @click="router.push('/forgotPassword')">忘记密码</el-link>
                </el-col>
            </el-row>
            <el-button type="primary" size="large" @click="login"> 立即登录 </el-button>
            <el-divider>
                <span style="color: grey;font-size: 13px">没有账号</span>
            </el-divider>
            <el-button type="warning" size="large" @click="router.push('/register')"> 注册账号 </el-button>
        </el-form>
    </div>
</template>

<style  lang="scss" scoped>

.content {
    padding: 20px 50px 50px 50px;
    :deep(.el-input-group__append) {
        padding: 0;
        overflow: hidden;
        .el-image {
            width: 100px;
            height: 40px;
            border-left: 0px;
            user-select: none;
            cursor: pointer;
            text-align: center;
        }
    }
    .el-button {
        width: 100%;
        margin-top: 10px;
    }
}
</style>
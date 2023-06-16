<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useStore} from "@/stores/counter";
import {ref} from "vue";
import Login from "@/components/welcome/Login.vue";
const store = useStore()
const logout = () => {
    get('/api/auth/logout',
        (message) => {
            ElMessage.success(message)
            store.auth.user = null
            router.push("/")
        })
}
const name = ref('')
const email = ref('')


const getData = () =>{
    get('/api/auth/getData',
        (message,data) => {
        console.log(message)
            console.log(data)
            name.value=data.username
            email.value=data.email
            ElMessage.success('获取信息成功')
        })
}
const getUser = ()=>{
    get('/api/user/me',(message,data)=>{
        console.log(data)
    })
}
</script>

<template>
  主页
  <el-button @click="logout" type="success">退出</el-button>
    <el-button @click="getData" type="success">获取数据</el-button>
  <p>name:{{name}}</p>
    <p>email:{{email}}</p>
    <el-button @click="getUser" type="success">获取用户信息</el-button>

</template>


<style>


</style>
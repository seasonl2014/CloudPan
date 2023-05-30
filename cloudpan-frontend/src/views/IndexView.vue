<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

const logout = () => {
    get('/api/auth/logout',
        (message) => {
            ElMessage.success(message)
            router.push("/")
        })
}
</script>

<template>
  主页
  <el-button @click="logout" type="success">退出</el-button>

</template>


<style>


</style>
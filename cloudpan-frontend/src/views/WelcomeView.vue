<script setup>

import {Key, Lock, User} from "@element-plus/icons-vue";
</script>

<template>
    <div class="login-container">
        <div class="login-card">
            <div class="title">
                <img src="@/assets/logo.png" />
            </div>
            <router-view />
        </div>
    </div>
</template>
<style  lang="scss" scoped>
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    min-height: 100%;
    height: 100vh;
    .login-card {
        width: 480px;
        border-radius: 20px;
        box-shadow: 0 0 10px #dcdfe6;
        background-color: #fff;
        overflow: hidden;
        .title {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 150px;
            img {
                height: 100%;
            }
        }

    }
}
</style>

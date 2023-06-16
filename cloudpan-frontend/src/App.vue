<script setup>
import { RouterLink, RouterView } from 'vue-router'
import {useStore} from "@/stores/counter";
import {get} from "@/net";
import router from "@/router";

const store = useStore()

if(store.auth.user = null){
    get("api/user/me",(message) =>{
        store.auth.user = message
        router.push('/index')
    },()=>{
        store.auth.user = null
    })
}

</script>

<template>
  <RouterView />
</template>

<style scoped>

</style>

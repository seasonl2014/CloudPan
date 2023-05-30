<script setup>

</script>

<template>
  <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
      <div style="flex: 1">
          <el-image style="width: 100%;height: 100%" fit="cover"
                src="https://www.toopic.cn/public/uploads/small/1642749399720164274939972.jpg"></el-image>
      </div>
      <div class="welcome-title">
          <div style="font-size: 30px;font-weight: bold">云盘</div>
      </div>
      <router-view />
  </div>
</template>

<style scoped>
.welcome-title {
    position: absolute;
    top: 30px;
    left: 30px;
    color: white;
    text-shadow: 0 0 10px black;
}
</style>
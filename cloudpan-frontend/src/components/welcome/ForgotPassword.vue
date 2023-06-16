<script setup>
import { Lock, Key, Message} from '@element-plus/icons-vue'
import {reactive,ref} from "vue";
import {ElMessage} from "element-plus";
import {post} from "@/net";
import router from "@/router";

const form = reactive({
    password: '',
    rePassword: '',
    email: '',
    code: ''
})
const validatePass = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('密码不能为空'))
    } else {
        if (form.rePassword !== '') {
            if (!formRef.value) return
            formRef.value.validateField('password_repeat', () => null)
        }
        callback()
    }
}
const validatePass2 = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次填写密码'))
    } else if (form.rePassword !== form.password) {
        callback(new Error("两次密码不相同"))
    } else {
        callback()
    }
}
const rules = {
    password: [
        { validator: validatePass, trigger: 'blur' },
        { min: 6, max: 20, message: '密码长度应该在6-20个字符', trigger: 'blur' },
    ],
    password_repeat: [
        { validator: validatePass2, trigger: 'blur' }
    ],
    email: [
        { required: true, message: '邮箱不能为空', trigger: 'blur' },
        { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] },
    ],
    code: [
        { required: true, message: '验证码不能为空', trigger: 'blur' }
    ]

}
const active = ref(1)
//校验邮箱是否合法
const isEmailValid = ref(false)
const formRef = ref()
//发送验证码冷却时间
const coldTime = ref(0)
const onValidate = (prop, isValid) =>{
    if(prop === 'email')
        isEmailValid.value = isValid
}


const validateEmail = () =>{
    coldTime.value = 60;
    post("/api/auth/validateEmail2",{
            email:form.email
        }, (message)=>{
            ElMessage.success(message)
            setInterval(()=>coldTime.value--, 1000)
        },(message)=>{
            ElMessage.error(message)
            coldTime.value = 0;
        }
    )
}
const recover1 = () =>{
    formRef.value.validate((isValid)=>{
        if(isValid){
            post("/api/auth/recover1",{
                email: form.email,
                code: form.code
            },(message) =>{
                active.value=2
            })
        } else {
            ElMessage.warning("请输入邮箱和验证码！")
        }
    })
}
const recover2 = () =>{
    formRef.value.validate((isValid)=>{
        if(isValid){
            post("/api/auth/recover2",{
                password: form.password
            },(message) =>{
                ElMessage.success(message)
                router.push('/')
            })
        } else {
            ElMessage.warning("请输入新密码！")
        }
    })
}
</script>
<template>

    <div class="content">
        <el-steps :active="active">
            <el-step title="Step 1" description="通过邮箱验证" />
            <el-step title="Step 2" description="输入新密码" />
        </el-steps>
        <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
            <el-form-item prop="password" v-if="active==2">
                <el-input
                        v-model="form.password"
                        placeholder="新密码"
                        type="password"
                        tabindex="2"
                        :prefix-icon="Lock"
                        size="large"
                        show-password
                />
            </el-form-item>
            <el-form-item prop="password_repeat" v-if="active==2">
                <el-input
                        v-model="form.rePassword"
                        placeholder="重复密码"
                        type="password"
                        tabindex="3"
                        :prefix-icon="Lock"
                        size="large"
                        show-password
                />
            </el-form-item>
            <el-form-item prop="email" v-if="active==1">
                <el-input
                        v-model="form.email"
                        placeholder="邮箱"
                        type="email"
                        tabindex="4"
                        :prefix-icon="Message"
                        size="large"
                />
            </el-form-item>
            <el-form-item prop="code" v-if="active==1">
                <el-input
                        v-model="form.code"
                        placeholder="验证码"
                        type="text"
                        tabindex="5"
                        :prefix-icon="Key"
                        maxlength="7"
                        size="large"
                >
                    <template #append>
                        <el-text :class="{'btnCode':true,'btnCodeClick': !isEmailValid || coldTime > 0}" @click="validateEmail">
                            {{ coldTime>0?'请等待'+coldTime+'秒':'发送验证码' }}
                        </el-text>
                    </template>
                </el-input>
            </el-form-item>
            <el-button type="success" size="large" @click="recover1" v-if="active==1"> 申请新密码 </el-button>
            <el-button type="success" size="large" @click="recover2" v-if="active==2"> 确认修改密码 </el-button>
            <div style="margin-top: 10px">
                <el-link type="primary" @click="router.push('/')">我已经拥有一个账号</el-link>
            </div>

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
  .btnCode {
    width: 100px;
    text-align: center;
    margin-top: 0px;
    cursor: pointer;
    -webkit-user-select: none;  /* 禁止 DIV 中的文本被鼠标选中 */
    -moz-user-select: none;     /* 禁止 DIV 中的文本被鼠标选中 */
    -ms-user-select: none;      /* 禁止 DIV 中的文本被鼠标选中 */
    user-select: none;             /* 禁止 DIV 中的文本被鼠标选中 */

  }
  .btnCodeClick {
    pointer-events: none;
    background-color: #dcdfe6;
  }
  .btnCode:hover{
    background-color: #dcdfe6;
  }
}

</style>
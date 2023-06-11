import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'welcome',
      component: () => import('@/views/WelcomeView.vue'),
      children: [
        {
          path: '',
          name: 'welcome-login',
          component: () => import('@/components/welcome/Login.vue')
        },
        {
          path: 'register',
          name: 'welcome-register',
          component: () => import('@/components/welcome/Register.vue')
        },
        {
          path: 'forgotPassword',
          name: 'welcome-recover',
          component: () => import('@/components/welcome/ForgotPassword.vue')
        }
      ]
    },
    {
      path: "/index",
      name: 'index',
      component: () => import('@/views/IndexView.vue')
    }

  ]
})

export default router

import {createRouter, createWebHistory} from "vue-router";
import NProgress from "@/utils/NProgress.ts";

const routes = [{
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue')
}, {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
}, {
    path: '/test',
    name: 'Test',
    component: () => import('@/views/Test.vue')
}]
const router = createRouter({
    history: createWebHistory(),
    routes
})
const whiteList = ['/login']


router.beforeEach((to, _from, next) => {
    NProgress.start()
    const token = localStorage.getItem('token')
    if (whiteList.includes(to.path)) {
        if (to.path === '/login' && token?.length) {
            next('/')
        }
        next()
        return
    }
    if (token?.length) {
        next()
    } else {
        next('/login')
    }
})
router.afterEach(() => {
    NProgress.done()
    window.scrollTo(0, 0)
})

export default router

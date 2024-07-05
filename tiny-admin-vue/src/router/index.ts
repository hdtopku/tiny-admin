import {createRouter, createWebHistory} from "vue-router";
import NProgress from "@/utils/NProgress.ts";
import {useUserStore} from "@/store";

const routes = [{
    path: '/',
    component: () => import('@/layout/Index.vue')
}, {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
},
    {
        // hide: true,
        path: '/:pathMatch(.*)*',
        component: () => import(`@/views/404.vue`),
    }
]
const router = createRouter({
    history: createWebHistory(),
    routes
})
const whiteList = ['/login']

let routeList
router.beforeEach((to, _from, next) => {
    NProgress.start()
    const token = localStorage.getItem('token')
    if (whiteList.includes(to.path)) {
        if (to.path === '/login' && token?.length) {
            return next('/')
        }
        return next()
    }
    if (token?.length) {
        if (!routeList) {
            routeList = useUserStore().getRouteList()
            if (routeList?.length) {
                router.addRoute(
                    {
                        component: () => import('@/layout/Index.vue'),
                        path: "/",
                        name: "Layout",
                        children: routeList,
                    }
                )
            }
            return next(to)
        } else {
            next()
        }
    } else {
        next('/login')
    }
})
router.afterEach(() => {
    NProgress.done()
    window.scrollTo(0, 0)
})

export default router

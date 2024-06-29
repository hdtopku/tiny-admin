import {defineAsyncComponent} from "vue"

const modules = import.meta.glob('../views/**/*.{vue,tsx}')

export const getAsyncComponent = (componentPath: string) => {
    for (const item in modules) {
        console.log(item, componentPath, item.includes(componentPath))
        if (item.includes(componentPath)) {
            // 使用异步组件的方式来动态加载组件
            // @ts-ignore
            return defineAsyncComponent(modules[item])
        }
    }
}
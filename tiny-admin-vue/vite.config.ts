import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import Components from 'unplugin-vue-components/vite'
import {AntDesignVueResolver} from 'unplugin-vue-components/resolvers'
import AutoImport from 'unplugin-auto-import/vite'
import {viteMockServe} from "vite-plugin-mock";

// https://vitejs.dev/config/
export default defineConfig(({command}) => {
    return {
        server: {
            port: 3000,
            open: true,
            proxy: {
                '/api': {
                    target: 'http://localhost:3000/mock',
                    changeOrigin: true,
                    rewrite: (path) => path.replace(/^\/api/, '')
                }
            }
        },
        plugins: [
            vue(),
            AutoImport({
                // 自动导入 Vue 相关函数，如：ref, reactive, toRef 等
                imports: ['vue']
            }),
            Components({
                resolvers: [AntDesignVueResolver({
                    importStyle: false, // css in js
                })],
            }),
            viteMockServe({
                mockPath: './mock/',
                enable: command === 'serve',
            })
        ],
        resolve:
            {
                alias: {
                    '@':
                        '/src'
                }
            }
    }
})

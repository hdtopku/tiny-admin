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
            host: '0.0.0.0',
            port: 3000,
            open: true,
            proxy: {
                '/api': {
                    target: 'http://localhost:8080/',
                    changeOrigin: true,
                    rewrite: (path) => path.replace(/^\/api/, '')
                },
                '/spring-boot-tutorial': {
                    target: 'ws://localhost:8081/spring-boot-tutorial',
                    changeOrigin: true,
                    ws: true,
                    rewrite: (path) => path.replace(/^\/spring-boot-tutorial/, '')
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

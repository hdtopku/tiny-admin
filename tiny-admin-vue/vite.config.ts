import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import Components from 'unplugin-vue-components/vite'
import {AntDesignVueResolver} from 'unplugin-vue-components/resolvers'
import AutoImport from 'unplugin-auto-import/vite'
import {viteMockServe} from "vite-plugin-mock";
import WindiCSS from 'vite-plugin-windicss'

// https://vitejs.dev/config/
export default defineConfig(({command}) => {
    return {
        base: '/',
        build: {
            outDir: 'dist',  // 确保输出路径是正确的
            rollupOptions: {
                input: 'index.html',  // 确保入口文件正确
            },
            esbuild: {
                minify: false, // 禁用压缩，看是否影响输出
            }
        },
        server: {
            host: '0.0.0.0',
            port: 3000,
            open: true,
            cors: true,
            proxy: {
                '/api': {
                    target: 'http://localhost:8080/',
                    changeOrigin: true,
                    rewrite: (path) => path.replace(/^\/api/, '')
                },
            }
        },
        plugins: [
            WindiCSS(),
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

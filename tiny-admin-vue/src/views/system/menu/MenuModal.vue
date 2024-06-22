<script setup lang="ts">
import {saveOrUpdateMenu} from "@/api/menu.ts";
import {message} from "ant-design-vue";

const props = defineProps({
  menuTree: []
})
const modalVisible = ref(true)
const isUpdate = ref(false)
const title = isUpdate.value ? '更新菜单' : '新增菜单'
const formRef = ref()
const form = reactive({
  name: '',
  url: '',
  component: '',
  icon: '',
  sort: 9999,
  parentId: null,
  type: 0,
  permission: '',
})
const rules: any = {
  parentId: [
    {required: false, message: '请选择父菜单', trigger: ['blur', 'change']},
  ],
  name: [
    {required: true, message: '请输入菜单名称', trigger: ['blur', 'change']},
    {min: 2, max: 50, message: '长度在 2 到 20 个字符', trigger: ['blur', 'change']},
  ],
  url: [
    {required: true, message: '请输入菜单路径', trigger: ['blur', 'change']},
    {min: 2, max: 200, message: '长度在 2 到 200 个字符', trigger: ['blur', 'change']},
  ],
  icon: [
    {required: true, message: '请输入菜单图标', trigger: ['blur', 'change']},
    {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: ['blur', 'change']},
  ],
  sort: [
    {required: true, message: '请输入菜单排序', trigger: ['blur', 'change']},
    {type: 'number', message: '请输入数字', trigger: ['blur', 'change']},
  ],
  type: [
    {required: true, message: '请选择菜单类型', trigger: ['blur', 'change']},
  ],
  permission: [
    {required: true, message: '请输入权限标识', trigger: ['blur', 'change']},
    {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: ['blur', 'change']},
  ],
}
const fetchMenuList = async () => {
  // const res = await getMenuList()
  // if (res.code === 0) {
  //   menuList.value = res.data
  // }
}
const activeKey = ref('1')
const loading = ref(false)
const handleOk = async () => {
  loading.value = true
  formRef.value.validate().then(() => {
    saveOrUpdateMenu(form).then(() => {
      message.success('操作成功')
      modalVisible.value = false
    })
  }).finally(() => {
    loading.value = false
  })
}
defineExpose({
  showModal(data) {
    isUpdate.value = !!data
    if (data) {
      Object.assign(form, data)
    }
    modalVisible.value = true
    fetchMenuList()
  },
})

const userInputPerm = ref(false)
const userInputComponent = ref(false)
const handleUrlChange = () => {
  if (form.url.startsWith('/')) {
    form.url = form.url.slice(1)
  }
  if (form.url.endsWith('.vue')) {
    form.url = form.url.slice(0, -4)
  }
  form.url = form.url
      .replace(/_/g, '-')
      .replace(/ /g, '-')
      .replace(/--/g, '-')
      .replace(/__/g, '-')
      .toLowerCase()
  if (!userInputPerm.value) {
    form.permission = `${form.url.toLowerCase().replace(/\//g, ':')}`
  }
  if (!userInputComponent.value) {
    form.component = `${form.url}`
  }
}

</script>

<template>
  <a-modal ok-text="提交" cancel-text="取消" :mask-closable="false" :open="modalVisible" :title="title"
           @ok="handleOk" @cancel="() => modalVisible = false">
    <template #footer>
      <a-button key="back" @click="modalVisible = false">取消</a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleOk">提交</a-button>
    </template>
    <a-tabs v-model:activeKey="activeKey" type="card">
      <a-tab-pane key="1" tab="菜单">
        <a-form :label-col="{span: 5}" class="pt-4" :model="form" :rules="rules" ref="formRef">
          <a-form-item help="若未选择，则为顶级菜单" label="父级菜单" name="parentId">
            <a-tree-select
                v-model:value="form.parentId"
                show-search
                style="width: 100%"
                :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                placeholder="请选择父级菜单"
                allow-clear
                tree-default-expand-all
                :tree-data="props.menuTree"
                tree-node-filter-prop="name"
                :field-names="{
      children: 'children',
      label: 'name',
      value: 'id',
    }"
            >
              <template #title="{ id: val, label }">
                <a-tag color="blue" v-if="val === form.parentId">{{ label }}</a-tag>
                <span v-else>{{ label }}</span>
              </template>
            </a-tree-select>

          </a-form-item>
          <a-form-item label="菜单名称" name="name">
            <a-input allow-clear autocomplete="off" v-model:value="form.name" placeholder="请输入菜单名称"/>
          </a-form-item>
          <a-form-item label="访问URL" name="url">
            <div class="flex items-center">
              <span>/</span>
              <a-input @change="handleUrlChange" allow-clear autocomplete="off" v-model:value="form.url"
                       placeholder="请输入访问URL"/>
            </div>
          </a-form-item>
          <a-form-item label="组件位置" name="component">
            <div class="flex items-center">
              <span class="">
                views/
              </span>
              <a-input @keyup="() => userInputComponent=true" allow-clear autocomplete="off"
                       v-model:value="form.component" placeholder="请输入组件位置">
                <template #suffix>
                  .vue
                </template>
              </a-input>
            </div>
          </a-form-item>
          <a-form-item label="权限标识" name="permission">
            <a-input @keyup="() => userInputPerm=true" allow-clear autocomplete="off" v-model:value="form.permission"
                     placeholder="请输入权限标识"/>
          </a-form-item>
          <a-form-item help="数字在 1-9999 之间。数值越大，排序越靠后" label="菜单排序" name="sort">
            <a-input-number allow-clear autocomplete="off" v-model:value="form.sort" :min="1" :max="9999"/>
          </a-form-item>
        </a-form>
      </a-tab-pane>
      <a-tab-pane key="2" tab="按钮">Content of Tab Pane 2</a-tab-pane>
    </a-tabs>

  </a-modal>
</template>

<style scoped>

</style>
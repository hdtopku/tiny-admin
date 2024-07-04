<script setup lang="ts">
import {saveOrUpdateMenu} from "@/api/menu.ts";
import {message} from "ant-design-vue";
import IconSelector from "@/components/IconSelector.vue";
import {useUserStore} from "@/store";
import useGlobal from "@/hooks/useGlobal.ts";

const props = defineProps({
  menuTree: Array as () => string[],
})
const open = ref(false)
const isUpdate = ref(false)
const title = ref('')
const formRef = ref()
const defaultForm =
    {
      name: '',
      url: '',
      component: '',
      icon: '',
      sort: 9999,
      hidden: false,
      parentId: null,
      type: 0,
      permission: '',
    }

const form: any = ref(defaultForm)
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
  sort: [
    {required: true, message: '请输入菜单排序', trigger: ['blur', 'change']},
    {type: 'number', message: '请输入数字', trigger: ['blur', 'change']},
  ],
  type: [
    {required: true, message: '请选择菜单类型', trigger: ['blur', 'change']},
  ],
}
const buttonRules: any = {
  name: [
    {required: true, message: '请输入按钮名称', trigger: ['blur', 'change']},
    {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: ['blur', 'change']},
  ],
  permission: [
    {required: true, message: '请输入权限标识', trigger: ['blur', 'change']},
    {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: ['blur', 'change']},
  ],
  sort: [
    {required: true, message: '请输入按钮排序', trigger: ['blur', 'change']},
    {type: 'number', message: '请输入数字', trigger: ['blur', 'change']},
  ],
}
const loading = ref(false)
const emits = defineEmits(['queryList'])
const {$bus} = useGlobal()
const btnFormRef = ref()
const handleOk = async () => {
  const submitForm = () => {
    loading.value = true
    saveOrUpdateMenu(form.value).then(() => {
      message.success('操作成功')
      open.value = false
      emits('queryList')
      useUserStore().refreshUserInfo().then(() => {
        $bus.emit('update-user-info')
      })
    }).finally(
        () => {
          loading.value = false
        }
    )
  }
  if (activeKey.value === '1') {
    formRef.value.validate().then(() => {
      submitForm()
    })
  } else {
    btnFormRef.value.validate().then(() => {
      submitForm()
    })
  }
}

const userInputPerm = ref(false)
const userInputComponent = ref(false)
const handleUrlChange = () => {
  if (isUpdate.value) return
  if (!userInputPerm.value) {
    form.value.permission = `${form.value.url.toLowerCase().replace(/\//g, ':')}`
  }
  if (!userInputComponent.value) {
    form.value.component = `${form.value.url}`
  }
}
const activeKey = ref('1')

watch(activeKey, () => {
  form.value.type = activeKey.value
})
watch(() => form.value.url, () => {
  while (form.value.url?.startsWith('/')) {
    form.value.url = form.value.url.slice(1)
  }
  if (form.value.url?.endsWith('.vue')) {
    form.value.url = form.value.url?.slice(0, -4)
  }
  if (form.value.url?.length) {
    form.value.url = form.value.url?.replace(/_/g, '-')
        .replace(/ /g, '-')
        .replace(/--/g, '-')
        .replace(/__/g, '-')
        .toLowerCase()
  }
})
watch(() => form.value.component, () => {
  if (form.value.component?.startsWith('views/')) {
    form.value.component = form.value.component.slice(6)
  }
  if (form.value.component?.endsWith('.vue')) {
    form.value.component = form.value.component.slice(0, -4)
  }
  while (form.value.component?.startsWith('/')) {
    form.value.component = form.value.component.slice(1)
  }
})
let currentMenu: any = {}
defineExpose({
  showModal(isEdit, item) {
    isUpdate.value = isEdit
    if (item) {
      Object.assign(form.value, {
        id: item.id,
        name: item.name,
        url: item.url,
        component: item.component,
        icon: item.icon,
        sort: item.sort || 9999,
        hidden: item.hidden,
        parentId: item.parentId,
        type: item.type,
        permission: item.permission,
      })
    } else {
      form.value = Object.assign({}, defaultForm)
    }
    open.value = true
    currentMenu = item
  }
})
</script>

<template>
  <a-drawer
      v-model:open="open"
      :root-style="{ color: 'blue' }"
      destroyOnClose
      placement="right"
      style="color: red"
      width="500"
  >
    <template #title>
      <span v-if="isUpdate">
        <span>修改菜单：</span>
        <span class="text-gray-400">{{ currentMenu.name }}</span>
      </span>
      <span v-else>新增菜单</span>
    </template>
    <template #footer>
      <a-space class="flex justify-end">
        <a-button key="back" @click="open = false">取消</a-button>
        <a-button key="submit" :loading="loading" type="primary" @click="handleOk">提交</a-button>
      </a-space>
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
            <a-input v-model:value="form.name" allow-clear autocomplete="off" placeholder="请输入菜单名称">
            </a-input>
          </a-form-item>
          <a-form-item label="访问URL" name="url">
            <div class="flex items-center">
              <span>/</span>
              <a-input @change="handleUrlChange" allow-clear autocomplete="off" v-model:value="form.url"
                       placeholder="请输入访问URL"/>
            </div>
          </a-form-item>
          <div class="grid grid-cols-2">
            <a-form-item :label-col="{span: 10}" help="数值越大，越靠后" label="菜单排序"
                         name="sort">
              <a-input-number v-model:value="form.sort" :defaultValue="9999" :max="9999" :min="1" allow-clear
                              autocomplete="off"/>
            </a-form-item>
            <a-form-item :label-col="{span: 10}" label="菜单图标" name="icon">
              <icon-selector v-model:value="form.icon"/>
            </a-form-item>
          </div>
          <a-form-item label="隐藏菜单">
            <a-switch v-model:checked="form.hidden" :checked-value="false" :un-checked-value="true"
                      checked-children="已显示" un-checked-children="已隐藏"/>
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
        </a-form>
      </a-tab-pane>
      <a-tab-pane key="2" tab="按钮">
        <a-form ref="btnFormRef" :rules="buttonRules" :label-col="{span: 5}" class="pt-4" :model="form">
          <a-form-item label="父级菜单" name="parentId">
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
          <a-form-item label="按钮名称" name="name">
            <a-input allow-clear autocomplete="off" v-model:value="form.name" placeholder="请输入按钮名称"/>
          </a-form-item>
          <a-form-item label="权限标识" name="permission">
            <a-input allow-clear autocomplete="off" v-model:value="form.permission"
                     placeholder="请输入权限标识"/>
          </a-form-item>
          <a-form-item label="按钮排序" help="数字在 1-9999 之间。数值越大，排序越靠后" name="sort">
            <a-input-number autocomplete="off" v-model:value="form.sort" :min="1" :max="9999"/>
          </a-form-item>
        </a-form>
      </a-tab-pane>
    </a-tabs>
  </a-drawer>
</template>

<style scoped>

</style>
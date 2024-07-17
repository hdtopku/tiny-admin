<script setup lang="ts">
import {saveOrUpdateMenu} from "@/api/menu.ts";
import {message} from "ant-design-vue";
import IconSelector from "@/components/IconSelector.vue";
import {useUserStore} from "@/store";
import useGlobal from "@/hooks/useGlobal.ts";
import {DefaultOptionType} from "ant-design-vue/es/vc-tree-select/TreeSelect";

const props = defineProps({
  menuTree: Array as () => DefaultOptionType[],
})
const getMenuTree = () => {
  const dfs=(menus: any) => {
    if(!menus?.length) return []
    let newMenus:any = []
    menus.filter((menu:any) => menu.type === 1).forEach((menu: any) => {
      let newMenu:any={...menu}
      if (menu?.children?.length) {
        newMenu.children=dfs(menu.children)
      }
      newMenus.push(newMenu)
    })
    return newMenus
  }
  return dfs(props.menuTree)
}
const menuTree = computed(() => getMenuTree())
const open = ref(false)
const isUpdate = ref(false)
const formRef = ref()
const defaultForm =
    {
      name: '',
      url: '',
      component: '',
      icon: [],
      sort: 9999,
      hidden: false,
      keepAlive: true,
      parentId: null,
      type: 0,
      unauthorizedStrategy: 0,
      permission: '',
    }

const form: any = ref({...defaultForm})
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
  type: [
    {required: true, message: '请选择菜单类型', trigger: ['blur', 'change']},
  ],
}
const buttonRules: any = {
  name: [
    {required: true, message: '请输入按钮名称', trigger: ['blur', 'change']},
    {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: ['blur', 'change']},
  ],
  // permission: [
  //   {required: true, message: '请输入权限标识', trigger: ['blur', 'change']},
  //   {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: ['blur', 'change']},
  // ],
}
const loading = ref(false)
const emits = defineEmits(['queryList'])
const {$bus} = useGlobal()
const btnFormRef = ref()
const handleOk = async () => {
  const submitForm = () => {
    loading.value = true
    if (!isUpdate.value && form.value.type === 2) {
      form.value.component = null
    }
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
  if (activeKey.value === 1) {
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
const activeKey = ref(1)

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
  showModal(item, isEdit) {
    open.value = true
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
        unauthorizedStrategy: item.unauthorizedStrategy || 0,
        keepAlive: item.keepAlive,
      })
    } else {
      form.value = {...defaultForm}
    }
    currentMenu = item
    activeKey.value = item?.type || 1
  }
})
</script>

<template>
  <a-drawer
      v-model:open="open"
      :root-style="{ color: 'blue' }"
      placement="right"
      style="color: red"
      width="500"
  >
    <template #title>
      <span v-if="isUpdate">
        <span>修改{{ form.type === 1 ? '菜单' : '按钮' }}：</span>
        <span class="text-gray-400">{{ currentMenu.name }}</span>
      </span>
      <span v-else>新增{{ form.type === 1 ? '菜单' : '按钮' }}</span>
    </template>
    <template #footer>
      <a-space class="flex justify-end">
        <a-button key="back" @click="open = false">取消</a-button>
        <a-button key="submit" :loading="loading" type="primary" @click="handleOk">提交</a-button>
      </a-space>
    </template>
    <a-tabs v-model:activeKey="activeKey" type="card">
      <template #rightExtra>
        <span class="text-gray-400">已选类别：<a-tag
            class="text-gray-400">{{ form.type === 1 ? '菜单' : '按钮' }}</a-tag></span>
      </template>
      <a-tab-pane :key="1" tab="菜单">
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
                :tree-data="menuTree"
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
          <a-form-item label="组件位置" name="component">
            <div class="flex items-center">
              <span class="">
                views/
              </span>
              <a-input v-model:value="form.component" allow-clear autocomplete="off"
                       placeholder="请输入组件位置" @keyup="() => userInputComponent=true">
                <template #suffix>
                  .vue
                </template>
              </a-input>
            </div>
          </a-form-item>
          <a-form-item label="权限标识" tooltip="若不填写该字段，则表示不设置权限，并对所有人开放。" name="permission">
            <a-input @keyup="() => userInputPerm=true" allow-clear autocomplete="off"
                     v-model:value="form.permission"
                     placeholder="请输入权限标识"/>
          </a-form-item>
          <div class="grid grid-cols-2">
            <a-form-item :label-col="{span: 10}" help="数值越大，越靠后" label="菜单排序">
              <a-input-number v-model:value="form.sort" :defaultValue="9999" :max="9999" :min="1" allow-clear
                              autocomplete="off"/>
            </a-form-item>
            <a-form-item :label-col="{span: 10}" label="菜单图标" name="icon">
              <icon-selector v-model:value="form.icon"/>
            </a-form-item>
          </div>
          <a-form-item :label-col="{span:12}" label="对未授权角色，重定向至"
                       tooltip="注意：若选择重定向至404页面，则该菜单、子菜单及其侧边栏将对未授权用户不可见。">
            <a-radio-group v-model:value="form.unauthorizedStrategy" name="radioGroup">
              <a-radio :value="0">404页面</a-radio>
              <a-radio :value="1">403页面</a-radio>
            </a-radio-group>
          </a-form-item>
          <div class="grid grid-cols-2">
            <a-form-item :label-col="{span: 10}" label="是否隐藏菜单">
              <a-switch v-model:checked="form.hidden" :checked-value="false" :un-checked-value="true"
                        checked-children="已显示" un-checked-children="已隐藏"/>
            </a-form-item>
            <a-form-item :label-col="{span: 12}" label="是否缓存页面"
                         tooltip="缓存页面后，页面切换不会重新加载，提高页面切换效率。">
              <a-switch v-model:checked="form.keepAlive" :checked-value="true" :un-checked-value="false"
                        checked-children="已缓存" un-checked-children="未缓存"/>
            </a-form-item>
          </div>
        </a-form>
      </a-tab-pane>
      <a-tab-pane :key="2" tab="按钮">
        <a-form ref="btnFormRef" :rules="buttonRules" :label-col="{span: 6}" class="pt-4" :model="form">
          <a-form-item label="父级菜单" name="parentId">
            <a-tree-select
                v-model:value="form.parentId"
                show-search
                style="width: 100%"
                :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                placeholder="请选择父级菜单"
                allow-clear
                tree-default-expand-all
                :tree-data="menuTree"
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
          <a-form-item label="接口URL"
                       tooltip="请求后端接口的URL，如：/system/sysmenu/saveorupdate。对于未授权用户，接口禁止调用。"
                       name="url">
            <div class="flex items-center">
              <span>/</span>
              <a-input @change="handleUrlChange" allow-clear autocomplete="off" v-model:value="form.url"
                       placeholder="请输入后端接口URL"/>
            </div>
          </a-form-item>
          <a-form-item help="隐藏按钮、显示但禁用时，需填写该字段才可以生效。"
                       label="权限标识"
                       tooltip="权限标识(如：user:add,user:edit,role:add等)。配合Auth.vue组件使用，对于未授权用户，控制按钮的显示/隐藏，可否点击等。"
                       name="permission">
            <a-input allow-clear autocomplete="off" v-model:value="form.permission"
                     placeholder="请输入按钮权限标识"/>
          </a-form-item>
          <a-form-item label="对未授权角色" name="sort">
            <a-radio-group v-model:value="form.unauthorizedStrategy" name="radioGroup">
              <a-radio :value="0">隐藏按钮</a-radio>
              <a-radio :value="1">显示但禁用</a-radio>
              <a-radio :value="2">可点击但提交失败</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-form>
      </a-tab-pane>
    </a-tabs>
  </a-drawer>
</template>

<style scoped>

</style>
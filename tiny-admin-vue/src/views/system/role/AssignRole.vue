<template>
  <a-drawer
      v-model:open="openDrawer"
      :root-style="{ color: 'blue' }"
      style="color: red"
      placement="right"
      width="500"
  >
    <template #title>{{ $t('分配菜单权限：') }}
      <a-tooltip :title="currentUser?.description">
        <span class="text-gray-400">{{ currentUser?.roleName }}</span>
      </a-tooltip>
    </template>
    <template #footer>
      <a-space class="flex justify-between">
        <a-space class="flex justify-end">
          <a-checkbox
              v-model:checked="checkState.checkAll"
              :indeterminate="checkState.indeterminate"
              @change="handleCheckAll"
          >{{
              $t('全选/全不选(')
            }}{{ checkedKeys.checked?.length + '/' + allNodes.length || 0 }})
          </a-checkbox>
          <span class="flex">
            <a-tooltip
                :arrow="false"
                :title="$t('展开全部')"
                mergedArrow
                @click="handleExpandAll"
            >
              <a-button
                  v-show="(expandedKeys?.length || 0) < allNodes.length"
                  size="small"
                  type="text"
              >{{ $t('展开') }}</a-button
              ></a-tooltip
            >
            <a-tooltip
                :arrow="false"
                :title="$t('折叠全部')"
                @click="handleExpandAll(false)"
            >
              <a-button
                  v-show="(expandedKeys?.length || 0) > 0"
                  size="small"
                  type="text"
              >{{ $t('折叠') }}</a-button
              ></a-tooltip
            >
          </span>
        </a-space>
        <a-space class="flex justify-end">
          <a-button @click="openDrawer = false">{{ $t('取消') }}</a-button>
          <a-button :loading="loading" type="primary" @click="handleSubmit">{{
              $t('提交')
            }}
          </a-button>
        </a-space>
      </a-space>
    </template>
    <template #extra></template>
    <a-spin :spinning="loading">
      <a-tree
          v-model:checkedKeys="checkedKeys"
          v-model:expandedKeys="expandedKeys"
          v-model:selectedKeys="selectedKeys"
          :field-names="fieldNames"
          :loading="loading"
          :tree-data="treeData"
          checkStrictly
          checkable
          defaultExpandAll
      >
      </a-tree>
    </a-spin>
  </a-drawer>
</template>
<script lang="ts" setup>
import {ref} from 'vue'
import {TreeProps} from 'ant-design-vue'
import {assignMenu} from '@/api/system/role.ts'
import {getMenuTree} from "@/api/system/menu.ts";

const openDrawer = ref<boolean>(false), currentUser = ref<any>({})
const expandedKeys = ref<string[]>(), selectedKeys = ref<string[]>([]),
    checkedKeys = ref<any>({checked: [], halfChecked: []})
const fieldNames: TreeProps['fieldNames'] = {title: 'name', key: 'id'}
const checkState = ref({indeterminate: true, checkAll: false})
let allNodes: any = [], treeData, loading = ref(false)

const emit = defineEmits(['queryList'])
const handleSubmit = () => {
  loading.value = true
  assignMenu(currentUser.value.id, checkedKeys.value.checked).then(() => {
    location.reload()
  }).finally(() => {
    loading.value = false
  })
}

const handleCheckAll = (e: any) => {
  if (e.target.checked) {
    checkedKeys.value = {checked: [...allNodes], halfChecked: []}
  } else {
    checkedKeys.value = {checked: [], halfChecked: []}
  }
}

watch(checkedKeys, () => {
  checkState.value = {
    checkAll: checkedKeys.value.checked?.length === allNodes.length,
    indeterminate:
        checkedKeys.value.checked?.length > 0 &&
        checkedKeys.value.checked?.length < allNodes.length,
  }
})
const handleExpandAll = (expandAll = true) => {
  if (expandAll) {
    expandedKeys.value = [...allNodes]
  } else {
    expandedKeys.value = []
  }
}

const openModal = (user: any) => {
  openDrawer.value = true
  if (!treeData) {
    loading.value = true
    getMenuTree().then((res: any) => {
      treeData = res
      allNodes = []
      const dfs = (nodes: any) => {
        nodes?.forEach((node: any) => {
          allNodes.push(node.id)
          if (node?.children?.length) {
            dfs(node.children)
          }
        })
      }
      dfs(treeData)
      openModal(user)
      return
    }).finally(() => {
      loading.value = false
    })
  }
  currentUser.value = user
  checkedKeys.value = {
    checked: user.menus.map((menu: any) => menu.id),
    halfChecked: [],
  }
  expandedKeys.value = [...allNodes]
}
defineExpose({
  openModal
})
</script>

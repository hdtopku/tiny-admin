<template>
  <a-drawer
      v-model:open="open"
      :root-style="{ color: 'blue' }"
      style="color: red"
      placement="right"
      width="500"
  >
    <template #title>
      分配菜单权限：
      <a-tooltip :title="currentUser?.description">
        <span class="text-gray-400">{{ currentUser?.roleName }}</span>
      </a-tooltip>
    </template>
    <template #footer>
      <a-space class="flex justify-between">
        <a-space class="flex justify-end">
          <a-checkbox v-model:checked="checkState.checkAll" :indeterminate="checkState.indeterminate"
                      @change="handleCheckAll">全选/全不选({{ checkedKeys.checked?.length+'/'+allNodes.length || 0 }})
          </a-checkbox>
          <span class="flex ">
          <a-tooltip :arrow="false" mergedArrow title="展开全部" @click="handleExpandAll">
            <a-button v-show="(expandedKeys?.length || 0) < allNodes.length" size="small"
                      type="text">展开</a-button></a-tooltip>
          <a-tooltip :arrow="false" title="折叠全部" @click="handleExpandAll(false)">
            <a-button v-show="(expandedKeys?.length || 0) > 0" size="small" type="text">折叠</a-button></a-tooltip>
        </span>
        </a-space>
        <a-space class="flex justify-end">
          <a-button @click="open = false">取消</a-button>
          <a-button type="primary" @click="handleSubmit">提交</a-button>
        </a-space>
      </a-space>
    </template>
    <template #extra>
    </template>
    <a-tree
        checkStrictly
        defaultExpandAll
        v-model:expandedKeys="expandedKeys"
        v-model:selectedKeys="selectedKeys"
        v-model:checkedKeys="checkedKeys"
        checkable
        :tree-data="treeData"
        :field-names="fieldNames"
    >
    </a-tree>
  </a-drawer>
</template>

<script lang="ts" setup>
import {ref} from 'vue';
import {TreeProps} from "ant-design-vue";
import {assignMenu} from "@/api/role.ts";
import {useUserStore} from "@/store";
import useGlobal from "@/hooks/useGlobal.ts";

const open = ref<boolean>(false);

const currentUser = ref<any>({})

const treeData: any = ref([])

const expandedKeys = ref<string[]>();
const selectedKeys = ref<string[]>([]);
const checkedKeys = ref<any>({checked: [], halfChecked: []});
const fieldNames: TreeProps['fieldNames'] = {
  title: 'name',
  key: 'id'
};

const emit = defineEmits(['queryList']);
const {$bus} = useGlobal()
const handleSubmit = () => {
  assignMenu(currentUser.value.id, checkedKeys.value.checked).then(() => {
    emit('queryList')
    open.value = false
    useUserStore().refreshUserInfo().then(() => {
      $bus.emit('update-user-info')
    })
  })
}

const checkState = ref({
  indeterminate: true,
  checkAll: false,
})
const handleCheckAll = (e: any) => {
  if (e.target.checked) {
    checkedKeys.value = {checked: [...allNodes], halfChecked: []}
  } else {
    checkedKeys.value = {checked: [], halfChecked: []}
  }
}
let allNodes: any = []
watch(checkedKeys, () => {
  checkState.value = {
    checkAll: checkedKeys.value.checked?.length === allNodes.length,
    indeterminate: checkedKeys.value.checked?.length > 0 && checkedKeys.value.checked?.length < allNodes.length
  }
})
const handleExpandAll = (expandAll = true) => {
  if (expandAll) {
    expandedKeys.value = [...allNodes]
  } else {
    expandedKeys.value = []
  }
}
defineExpose({
  show: (user: any, tree: any) => {
    open.value = true;
    currentUser.value = user;
    checkedKeys.value = {checked: user.menus.map((menu: any) => menu.id), halfChecked: []}
    treeData.value = tree
    allNodes=[]
    const dfs = (nodes: any) => {
      nodes?.forEach((node: any) => {
        allNodes.push(node.id)
        if (node?.children?.length) {
          dfs(node.children)
        }
      })
    }
    dfs(tree)
    expandedKeys.value = [...allNodes]
  },
})
</script>


<template>
  <a-drawer
      v-model:open="open"
      :root-style="{ color: 'blue' }"
      style="color: red"
      title="分配菜单权限"
      placement="right"
      width="500"
  >
    <template #footer>
      <a-space class="flex justify-end">
        <a-button @click="open = false">取消</a-button>
        <a-button type="primary" @click="handleSubmit">提交</a-button>
      </a-space>
    </template>
    <template #extra>
      <a-space class="flex justify-end">
        <a-checkbox>展开/折叠</a-checkbox>
        <a-checkbox @change="onCheckAllChange">全选/全不选</a-checkbox>
      </a-space>
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

const open = ref<boolean>(false);

const currentUser = ref<any>({})

const treeData: any = ref([])

const expandedKeys = ref<string[]>();
const selectedKeys = ref<string[]>();
const checkedKeys = ref<any>();
const fieldNames: TreeProps['fieldNames'] = {
  title: 'name',
  key: 'id'
};
watch(expandedKeys, () => {
  console.log('expandedKeys', expandedKeys);
});
watch(selectedKeys, () => {
  console.log('selectedKeys', selectedKeys);
});
watch(checkedKeys, () => {
  console.log('checkedKeys', checkedKeys);
});
const emit = defineEmits(['queryList']);
const handleSubmit = () => {
  assignMenu(currentUser.value.id, checkedKeys.value.checked).then(() => {
    emit('queryList')
    open.value = false;
  })
}

const onCheckAllChange = (e: any) => {
  let res: any = []
  const dfs = (node: any) => {
    if(!node) return
    res.push(node.id)
    if (node?.children?.length) {
      node.children.forEach((item: any) => {
        dfs(item)
      })
    }
  }
  if (e.target.checked) {
    treeData.value.map((item: any) => dfs(item))
    checkedKeys.value = {checked: res};
  } else {
    checkedKeys.value = {checked: []};
  }
}
defineExpose({
  show: (user: any, tree: any) => {
    open.value = true;
    currentUser.value = user;
    checkedKeys.value = user.menus.map((menu: any) => menu.id);
    treeData.value = tree;
  },
})
</script>


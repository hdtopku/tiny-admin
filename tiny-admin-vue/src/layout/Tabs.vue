<script lang="ts" setup>
import {ref} from 'vue';
import {useMenuStore} from "@/store";
import {Key} from "ant-design-vue/es/_util/type";

const panes = ref<{ title: string; content: string; key: string; closable?: boolean }[]>(useMenuStore().openedTabs);

const activeKey = ref(useMenuStore().activeTab)

const onEdit = (e: MouseEvent | Key | KeyboardEvent, action: "add" | "remove") => {
  if (action === 'add') {
    // Handle add action
  } else {
    useMenuStore().removeTab((e as Key).toString());
  }
}
watch(activeKey, (newVal) => {
  useMenuStore().changeTab(newVal.toString())
})
watch(() => useMenuStore().activeTab, (newVal) => {
  activeKey.value = newVal
})
</script>


<template>
  <div class="w-full">
    <a-tabs v-model:activeKey="activeKey" hide-add type="editable-card" @edit="onEdit">
      <a-tab-pane v-for="pane in panes" :key="pane.key" :closable="panes.length > 1 && pane.closable"
                  :tab="pane.title">
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<style scoped>
::v-deep(.ant-tabs-nav) {
  margin: 0;
}

::v-deep(.ant-tabs) {
  line-height: 1;
}
</style>
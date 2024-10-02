<template>
  <a-table :columns="columns" :data-source="data" :row-selection="rowSelection"/>
</template>
<script lang="ts" setup>
import {computed, ref, unref} from 'vue';

interface DataType {
  key: string | number;
  name: string;
  age: number;
  address: string;
}

const columns = [
  {
    title: 'Name',
    dataIndex: 'name',
  },
  {
    title: 'Age',
    dataIndex: 'age',
  },
  {
    title: 'Address',
    dataIndex: 'address',
  },
];

const data: DataType[] = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key: i,
    name: `Edward King ${i}`,
    age: 32,
    address: `London, Park Lane no. ${i}`,
  });
}

const selectedRowKeys = ref<DataType['key'][]>([1, 2, 3]); // Check here to configure the default column

const onSelectChange = (changableRowKeys: string[]) => {
  console.log('selectedRowKeys changed: ', changableRowKeys);
  selectedRowKeys.value = changableRowKeys;
};

const rowSelection = computed(() => {
  return {
    selectedRowKeys: unref(selectedRowKeys),
    onChange: onSelectChange,
    hideDefaultSelections: true,
  };
});
</script>


<template>
  <div>
    <a-drawer
        v-model:open="open"
        cancel-text="Cancel"
        ok-text="Submit"
        :size="width < 768 ? 'default' : 'large'"
        destroy-on-close
        title="Assign Product"
        @ok="handleOk"
    >
      <template #footer>
        <a-space class="flex justify-end">
          <a-button key="back" @click="open = false">Cancel</a-button>
          <a-button
              key="submit"
              :loading="loading"
              type="primary"
              @click="handleOk"
          >
            Submit
          </a-button>
        </a-space>
      </template>
      <Search :show-add="false" :loading="loading" search-class="" @query-list="queryList" />
      <a-table
          :columns="columns"
          :dataSource="dataSource"
          :pagination="pagination"
          :row-selection="rowSelection"
          class="mt-4"
          @change="handleTableChange"
          :loading="loading"
      >
        <template #bodyCell="{ record, column }">
          <template v-if="column.dataIndex === 'album'">
            <ImageCarousel :img-urls="record?.album || []" :width="'100px'" />
          </template>
          <template v-else-if="column.dataIndex === 'productName'">
            <ToolTip :length="10" :content="record.productName" />
          </template>
          <template v-else-if="column.dataIndex === 'goodsDesc'">
            <a-tooltip :arrow="false">
              <template #title>
                <span>{{ record.goodsDesc }}</span>
              </template>
              <span>{{ record.goodsDesc?.substring(0, 10) }}</span>
              <span v-if="record.goodsDesc?.length > 10">...</span>
            </a-tooltip>
          </template>
        </template>
      </a-table>
    </a-drawer>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { message } from 'ant-design-vue';
import ImageCarousel from '@/views/pms/product/ImageCarousel.vue';
import { execQuery } from '@/api/pms/product.ts';
import { useDebounceFn, useWindowSize } from '@vueuse/core';

const { queryList: emitQueryList = () => {} } = defineProps({
  queryList: Function,
});
const { width } = useWindowSize();

const open = ref<boolean>(false),
    isUpdate = ref<boolean>(false),
    brandInfo = ref<any>();
const emit = defineEmits(['queryList']),
    loading = ref(false),
    dataSource = ref([]);
let pagination: any = {},
    searchParams: any = { keyword: '', enabled: true, pageNum: 1, pageSize: 10 };

const queryList = (params = {}) => {
  loading.value = true;
  searchParams = { ...searchParams, ...params };
  execQuery(searchParams)
      .then((res: any) => {
        dataSource.value = res.records;
        res.records.forEach((item: any) => {
          item.key = item.id;
        });
        pagination = { current: res.current, pageSize: res.size, total: res.total };
      })
      .finally(() => {
        loading.value = false;
      });
};

const handleTableChange = (pagination: any) => {
  searchParams.pageNum = pagination.current;
  searchParams.pageSize = pagination.pageSize;
  queryList();
};

const debounceQuery = useDebounceFn(queryList, 500);
watch(() => searchParams.keyword, debounceQuery);

const selectedGoodsIds: Ref<(string | number)[]> = ref([]);
const rowSelection = computed(() => {
  return {
    checkStrictly: false,
    selectedRowKeys: unref(selectedGoodsIds),
    preserveSelectedRowKeys: true,
    onSelectAll: (selected, selectedRows, changeRows) => {
      if (selected) {
        selectedGoodsIds.value.push(...changeRows.map((item: any) => item.id));
      } else {
        selectedGoodsIds.value = selectedGoodsIds.value.filter(
            (id: any) => !changeRows.some((item: any) => item.id === id)
        );
      }
    },
    onSelect: (record, selected: boolean) => {
      if (selected) {
        selectedGoodsIds.value.push(record.id);
      } else {
        selectedGoodsIds.value = selectedGoodsIds.value.filter((id: any) => id !== record.id);
      }
    },
  };
});

let submit: Function;
const handleOk = () => {
  loading.value = true;
  submit(flashSaleId, selectedGoodsIds.value)
      .then(() => {
        message.success('Operation successful');
        open.value = false;
        emitQueryList();
      })
      .finally(() => {
        loading.value = false;
      });
};
let flashSaleId: string;
defineExpose({
  openModal: (record: any, getSelectIds: Function, submitFunc: Function) => {
    open.value = true;
    if (!dataSource.value?.length) {
      queryList();
    }
    getSelectIds(record.id).then((res: any) => {
      selectedGoodsIds.value = res;
    });
    isUpdate.value = !!record.id;
    isUpdate.value = true;
    brandInfo.value = {
      brandId: null,
      enabled: true,
      sort: 9999,
      remark: '',
      ...record,
    };
    flashSaleId = record.id;
    submit = submitFunc;
  },
});

const columns: any = [
  {
    title: 'Product Album',
    dataIndex: 'album',
    key: 'album',
    width: 100,
  },
  {
    title: 'Product Name',
    dataIndex: 'productName',
    key: 'productName',
    width: 150,
  },
  {
    title: 'Sale Price',
    dataIndex: 'salePrice',
    key: 'salePrice',
    width: 100,
  },
  {
    title: 'Market Price',
    dataIndex: 'marketPrice',
    key: 'marketPrice',
    width: 100,
  },
];
</script>

<style scoped>
/* Optional: Add scoped styles here */
</style>

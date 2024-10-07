<template>
  <div>
    <div class="p-4">
      <div class="flex mb-4">
        <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
          <a-button
              :loading="switchLoading"
              type="primary"
              @click="handleAdd"
          >{{ $t('新增') }}
          </a-button
          >
          <a-input
              id="keyword"
              v-model:value="searchForm.keyword"
              :placeholder="$t('搜索备注、商品id')"
              allow-clear
              autocomplete="off"
              class="text-left"
              type="text"
              @keyup.enter.native="queryList"
          >
            <template #prefix>
              <a-switch
                  v-model:checked="searchForm.status"
                  :checked-children="$t('已启用')"
                  :loading="switchLoading"
                  :un-checked-children="$t('已禁用')"
                  class="flex-shrink-0"
                  @change="queryByStatus"
              />
            </template>
            <template #suffix>
              <a-button
                  :loading="switchLoading"
                  type="primary"
                  @click="queryList"
              >{{ $t('搜索') }}
              </a-button
              >
            </template>
          </a-input>
        </div>
      </div>
    </div>

    <a-table
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pagination"
        :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
        @change="handleTableChange"
    >
      <template #bodyCell="{ record, index, column }">
        <template v-if="column.key === 'index'">
          {{ index + 1 }}
        </template>
        <template v-else-if="column.dataIndex === 'goodsId'">
          <a-typography-text copyable>{{ record.goodsId }}</a-typography-text>
        </template>
        <template v-else-if="column.dataIndex === 'remark'">
          <span v-if="record.remark?.length <= 20">{{ record.remark }}</span>
          <a-tooltip v-else :arrow="false">
            <template #title>
              <span>{{ record.remark }}</span>
            </template>
            <span>{{ record.remark?.substring(0, 20) }}</span>
            <span v-if="record.remark?.length > 20">...</span>
          </a-tooltip>
        </template>
        <template v-else-if="column.dataIndex === 'sort'">
          {{ record.sort }}
        </template>

        <template v-else-if="column.key === 'operation'">
          <div class="grid grid-cols-2 items-center justify-center">
            <a-popconfirm
                :cancel-text="$t('否')"
                :ok-text="$t('是')"
                :title="
                record.status ? $t('是否禁用该品牌？') : $t(' 是否启用该品牌？')
              "
                @confirm="
                () => {
                  confirmChangeStatus(record)
                }
              "
            >
              <template #icon>
                <question-circle-outlined style="color: red"/>
              </template>
              <a-switch
                  v-model:checked="record.status"
                  :checked-children="$t('已启用')"
                  :checked-value="1"
                  :loading="record.loading"
                  :un-checked-children="$t('已禁用')"
                  :un-checked-value="0"
                  class="flex-shrink-0"
                  size="small"
                  @click="
                  () => {
                    changeStatus(record)
                  }
                "
              />
            </a-popconfirm>
            <a-dropdown placement="bottom" trigger="hover">
              <a-button class="flex items-center" size="small" type="link"
              >{{ $t('操作') }}
                <DownOutlined/>
              </a-button>
              <template #overlay>
                <a-menu class="text-center">
                  <a-menu-item>
                    <a-button type="link" @click="() => handleEdit(record)">{{
                        $t('编辑推荐新品')
                      }}
                    </a-button>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm
                        :cancel-text="$t('否')"
                        :ok-text="$t('是')"
                        ok-type="danger"
                        @confirm="
                        () => {
                          deleteBrand(record.id)
                        }
                      "
                    >
                      <template #icon>
                        <question-circle-outlined style="color: red"/>
                      </template>
                      <template #title>
                        <div>{{ $t('是否删除推荐新品？') }}</div>
                        <a-tag class="my-2" color="red">{{
                            record.brandName
                          }}
                        </a-tag>
                      </template>
                      <a-button danger type="link">{{
                          $t('删除推荐品牌')
                        }}
                      </a-button>
                    </a-popconfirm>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
        </template>
      </template>
    </a-table>
    <NewGoodsModal @query-list="queryList" ref="modalRef"></NewGoodsModal>
    <EditNewGoodsModal
        ref="editModalRef"
        @query-list="queryList"
    ></EditNewGoodsModal>
  </div>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {DownOutlined, QuestionCircleOutlined} from '@ant-design/icons-vue'
import {useDebounceFn} from '@vueuse/core'
import NewGoodsModal from '@/views/sms/newgoods/NewGoodsModal.vue'
import {getSmsNewGoodsPage} from '@/api/sms/newGoods.ts'
import EditNewGoodsModal from '@/views/sms/newgoods/EditNewGoodsModal.vue'

const switchLoading = ref(true)
const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 0,
})
const searchForm = ref({
  keyword: '',
  status: true,
  pageNum: pagination.value.current,
  pageSize: pagination.value.pageSize,
})
const dataSource = ref([])

const queryList = () => {
  switchLoading.value = true
  searchForm.value.keyword = searchForm.value.keyword.trim()
  getSmsNewGoodsPage(searchForm.value)
      .then((res: any) => {
        dataSource.value = res.records
        pagination.value.total = res.total
      })
      .finally(() => {
        switchLoading.value = false
      })
}
queryList()
let brandList: any = []
const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchForm.value.keyword, debounceQuery)
const queryByStatus = () => {
}

const columns: any = [
  {
    title: t('序号'),
    dataIndex: 'index',
    key: 'index',
    width: 60,
    align: 'center',
  },
  {
    title: t('商品id'),
    dataIndex: 'goodsId',
    key: 'goodsId',
    width: 100,
    align: 'center',
  },
  {
    title: t('备注'),
    dataIndex: 'remark',
    key: 'remark',
    width: 200,
    align: 'center',
  },
  {
    title: t('排序'),
    dataIndex: 'sort',
    key: 'sort',
    width: 100,
    align: 'center',
  },
  {
    title: t('操作'),
    key: 'operation',
    width: 160,
    fixed: 'right',
  },
]

const modalRef = ref()
const handleAdd = () => {
  modalRef.value.showModal(brandList)
}
const editModalRef = ref()
const handleEdit = (record) => {
  editModalRef.value.showModal(record)
}
const deleteBrand = (id) => {
  console.log(id)
}
const confirmChangeStatus = (record) => {
  console.log(record)
}
const changeStatus = (record) => {
  console.log(record)
}
const handleTableChange = (pagination, filters, sorter) => {
  console.log(pagination, filters, sorter)
}
</script>

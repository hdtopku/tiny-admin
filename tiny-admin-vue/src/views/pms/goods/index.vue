<template>
  <div>
    <div class="p-4">
      <div class="flex mb-4">
        <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
          <a-button type="primary" @click="handleAdd">{{
              $t('新增')
            }}
          </a-button>
          <a-input
              id="keyword"
              v-model:value="searchForm.keyword"
              :placeholder="$t('搜索商品名称、副标题、简介、品牌名')"
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
              <a-button type="primary" @click="queryList">{{
                  $t('搜索')
                }}
              </a-button>
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
        <template v-else-if="column.dataIndex === 'albumList'">
          <ImageCarousel :img-urls="record?.albumList || []"/>
        </template>
        <template v-else-if="column.dataIndex === 'goodsName'">
          <a-tooltip :arrow="false">
            <template #title>
              <span>{{ record.goodsName }}</span>
            </template>
            <span>{{ record.goodsName?.substring(0, 10) }}</span>
            <span v-if="record.goodsName?.length > 10">...</span>
          </a-tooltip>
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
                  confirmChangeStatus()
                }
              "
            >
              <template #icon>
                <question-circle-outlined style="color: red"/>
              </template>
              <a-switch
                  v-model:checked="record.status"
                  :checked-children="$t('已启用')"
                  :loading="record.loading"
                  :un-checked-children="$t('已禁用')"
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
                        $t('编辑商品')
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
                          deleteBrand
                        }
                      "
                    >
                      <template #icon>
                        <question-circle-outlined style="color: red"/>
                      </template>
                      <template #title>
                        <div>{{ $t('是否删除品牌？') }}</div>
                        <a-tag class="my-2" color="red">{{
                            record.brandName
                          }}
                        </a-tag>
                      </template>
                      <a-button danger type="link">{{
                          $t('删除品牌')
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
    <GoodsModal @query-list="queryList" ref="goodsModalRef"/>
  </div>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {DownOutlined, QuestionCircleOutlined} from '@ant-design/icons-vue'
import {getGoodsPage} from '@/api/pms/goods.ts'
import ImageCarousel from '@/views/pms/goods/ImageCarousel.vue'
import {useDebounceFn} from '@vueuse/core'
import GoodsModal from '@/views/pms/goods/GoodsModal.vue'

const handleAdd = () => {
}
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
const switchLoading = ref(false)

const queryList = () => {
  getGoodsPage(searchForm.value).then((res: any) => {
    dataSource.value = res.records
    pagination.value.total = res.total
  })
}
queryList()
const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchForm.value.keyword, debounceQuery)
const queryByStatus = () => {
}

const columns: any = [
  {
    title: t('序号'),
    key: 'index',
    width: 60,
  },
  {
    title: t('商品图册'),
    dataIndex: 'albumList',
    key: 'albumList',
    width: 100,
  },
  {
    title: t('商品名称'),
    dataIndex: 'goodsName',
    key: 'goodsName',
    width: 150,
  },
  {
    title: t('商品简介'),
    dataIndex: 'goodsDesc',
    key: 'goodsDesc',
    width: 150,
  },
  {
    title: t('市场价格'),
    dataIndex: 'marketPrice',
    key: 'marketPrice',
    width: 100,
  },
  {
    title: t('促销价格'),
    dataIndex: 'promotionPrice',
    key: 'promotionPrice',
    width: 100,
  },
  {
    title: t('操作'),
    key: 'operation',
    fixed: 'right',
    width: 160,
  },
]

const dataSource = ref([])
const handleTableChange = (pagination: any) => {
  searchForm.value.pageNum = pagination.current
  searchForm.value.pageSize = pagination.pageSize
  queryList()
}

const goodsModalRef = ref()
const handleEdit = (record: any) => {
  goodsModalRef.value.showModal(record)
}
const changeStatus = (record) => {
  console.log(record)
}
const confirmChangeStatus = () => {
}
const deleteBrand = () => {
}
</script>

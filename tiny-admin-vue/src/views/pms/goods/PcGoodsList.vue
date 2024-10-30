<script lang="ts" setup>
import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import ImageCarousel from "@/views/pms/goods/ImageCarousel.vue"
import {t} from "@/utils/i18n.ts"
import {message, Pagination} from "ant-design-vue"
import {deleteGoodsById, saveOrUpdateGoods} from "@/api/pms/goods.ts";

const {dataSource, pagination} = defineProps({
  dataSource: Array,
  pagination: Pagination,
})

const deleteRecord = (id: string) => {
  deleteGoodsById(id)
}

const emit = defineEmits(['openModal', 'queryList'])
const confirmChangeStatus = (record: any) => {
  record.loading = true
  const {loading, ...rest} = record
  rest.status =!rest.status
  saveOrUpdateGoods(rest).then(() => {
    record.status = rest.status
    message.success(t('操作成功'))
  }).finally(() => {
    record.loading = false
  })
}
const openModal = (record: any) => {
  emit('openModal', record)
}
const handleTableChange = (pagination: any) => {
  emit('queryList', {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  })
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
    title: t('库存'),
    dataIndex: 'stock',
    key: 'stock',
    width: 100,
  },
  {
    title: t('操作'),
    key: 'operation',
    fixed: 'right',
    width: 160,
  },
]
</script>

<template>

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
              @confirm="confirmChangeStatus(record)"
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
                @click="() => {record.status =!record.status}"
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
                  <a-button type="link" @click="() => openModal(record)">{{
                      $t('编辑商品')
                    }}
                  </a-button>
                </a-menu-item>
                <a-menu-item>
                  <a-popconfirm
                      :cancel-text="$t('否')"
                      :ok-text="$t('是')"
                      ok-type="danger"
                      @confirm="deleteRecord(record.id)"
                  >
                    <template #icon>
                      <question-circle-outlined style="color: red"/>
                    </template>
                    <template #title>
                      <div>{{ $t('是否删除商品？') }}</div>
                      <a-tag class="my-2" color="red">{{
                          record.goodsName
                        }}
                      </a-tag>
                    </template>
                    <a-button danger type="link">{{
                        $t('删除商品')
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
</template>
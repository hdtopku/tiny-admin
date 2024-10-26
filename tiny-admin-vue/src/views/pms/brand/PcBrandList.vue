<script lang="ts" setup>
import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import {message, Pagination} from "ant-design-vue";
import {t} from "@/utils/i18n.ts";
import {deleteBrandById, saveOrUpdateBrand} from "@/api/pms/brand.ts";

const props: any = defineProps({
  dataSource: Array,
  pagination: Pagination,
})

const columns: any = [
  {
    title: t('序号'),
    key: 'index',
    width: 60,
  },
  {
    title: t('品牌logo'),
    dataIndex: 'logo',
    key: 'logo',
    width: 100,
  },
  {
    title: t('品牌名'),
    dataIndex: 'brandName',
    key: 'brandName',
    width: 150,
  },
  {
    title: t('品牌简介'),
    dataIndex: 'brandDesc',
    key: 'brandDesc',
    width: 200,
  },
  {
    title: t('品牌故事'),
    dataIndex: 'brandStory',
    key: 'brandStory',
    width: 200,
  },
  {
    title: t('操作'),
    key: 'operation',
    fixed: 'right',
    width: 160,
  },
]

const emit = defineEmits(['openModal', 'queryList'])
const handleTableChange = (pagination: any) => {
  emit('queryList', {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  })
}
const confirmChangeStatus = (record: any) => {
  record.status = !record.status
  saveOrUpdateBrand(record).then(() => {
    emit('queryList')
  })
}
const openModal = (record: any) => {
  emit('openModal', record)
}
const deleteRecord = (id: any) => {
  deleteBrandById(id).then(() => {
    message.success(t('删除成功'))
    emit('queryList')
  })
}
</script>

<template>
  <a-table
      :columns="columns"
      :dataSource="props.dataSource"
      :pagination="props.pagination"
      :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
      @change="handleTableChange"
  >
    <template #bodyCell="{ record, index, column }">
      <template v-if="column.key === 'index'">
        {{ index + 1 }}
      </template>
      <template v-else-if="column.dataIndex === 'logo'">
        <a-avatar :src="record.logo" shape="square" size="large"/>
      </template>
      <template v-else-if="column.dataIndex === 'brandDesc'">
        <a-tooltip :arrow="false">
          <template #title>
            <span>{{ record.brandDesc }}</span>
          </template>
          <span>{{ record.brandDesc?.substring(0, 10) }}</span>
          <span v-if="record.brandDesc?.length > 10">...</span>
        </a-tooltip>
      </template>
      <template v-else-if="column.dataIndex === 'brandStory'">
        <a-tooltip :arrow="false">
          <template #title>
            <span>{{ record.brandStory }}</span>
          </template>
          <span>{{ record.brandStory?.substring(0, 10) }}</span>
          <span v-if="record.brandStory?.length > 10">...</span>
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
                :loading="record.loading"
                :un-checked-children="$t('已禁用')"
                class="flex-shrink-0"
                size="small"
                @click="record.status=!record.status"
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
                  <a-button
                      type="link"
                      @click="() => openModal(record)"
                  >{{ $t('编辑品牌') }}
                  </a-button
                  >
                </a-menu-item>
                <a-menu-item>
                  <a-popconfirm
                      :cancel-text="$t('否')"
                      :ok-text="$t('是')"
                      ok-type="danger"
                      @confirm="
                        () => {
                          deleteRecord(record.id)
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
</template>

<style scoped>

</style>
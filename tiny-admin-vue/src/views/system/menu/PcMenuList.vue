<script setup lang="ts">
import {QuestionCircleOutlined} from "@ant-design/icons-vue"
import {CreateIcon} from "@/components/CustomIcon.ts"
import {deleteMenuById, saveOrUpdateMenu} from "@/api/system/menu.ts"
import {message} from "ant-design-vue"
import {t} from "@/utils/i18n.ts"

const {dataSource, loading} = defineProps({
  dataSource: Array,
  loading: Boolean,
})

const emit = defineEmits(['queryList', 'openModal'])
const deleteMenu = (menuId: String) => {
  deleteMenuById(menuId).then(() => {
    emit('queryList')
    message.success(t('删除成功'))
  })
}
const saveOrUpdate = (record: any) => {
  saveOrUpdateMenu(record).then(() => {
    emit('queryList')
    message.success(t('保存成功'))
  })
}
const columns: any = [
  {
    title: t('菜单名称'),
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: t('菜单类型'),
    dataIndex: 'type',
    key: 'type',
    width: 100,
  },
  {
    title: t('排序'),
    dataIndex: 'sort',
    key: 'sort',
    width: 60,
  },
  {
    title: t('权限码'),
    dataIndex: 'permission',
    key: 'permission',
    width: 120,
  },
  {
    title: t('按钮策略'),
    dataIndex: 'unauthorizedStrategy',
    key: 'permission',
    width: 120,
  },
  {
    title: t('创建时间'),
    dataIndex: 'createTime',
    key: 'createTime',
  },
  {
    title: t('更新时间'),
    dataIndex: 'updateTime',
    key: 'updateTime',
  },
  {
    title: t('操作'),
    key: 'operation',
    fixed: 'right',
  },
]
</script>

<template>

<a-table
      :columns="columns"
      :data-source="dataSource"
      :loading="loading"
      :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
      row-key="id"
      :pagination="false"
  >
    <template #bodyCell="{ record, column }">
      <template v-if="column.dataIndex === 'name'">
        <CreateIcon :icon="record.icon"/>
        {{ record.name }}
      </template>
      <template v-if="column.dataIndex === 'type'">
        <template v-if="record.type === 0">{{ $t('目录') }}</template>
        <template v-if="record.type === 1">{{ $t('菜单') }}</template>
        <template v-if="record.type === 2">{{ $t('按钮') }}</template>
      </template>
      <template v-if="column.key === 'operation'">
        <a-button type="link" @click="emit('openModal', record)">{{
            $t('修改')
          }}
        </a-button>
        <Auth :has-permission="'system:sysmenu:deletebyids'">
          <a-popconfirm
              :cancel-text="$t('否')"
              :ok-text="$t('是')"
              :title="$t('是否删除该菜单？')"
              ok-type="danger"
              @confirm="() => deleteMenu(record.id)"
          >
            <template #icon>
              <question-circle-outlined style="color: red"/>
            </template>
            <a-button danger type="link">{{ $t('删除') }}</a-button>
          </a-popconfirm>
        </Auth>
        <a-button
            v-show="record.type === 1"
            link
            type="link"
            @click="() => saveOrUpdate({ parentId: record.id }, false)"
        >{{ $t('新增') }}
        </a-button
        >
      </template>
    </template>
</a-table>
</template>

<style scoped>

</style>
<template>
  <a-result
      :sub-title="$t('对不起，你没有权限访问此页面！请联系管理员。')"
      status="403"
      title="403"
  >
    <template #extra>
      <a-button type="primary" @click="handleClick">{{
          $t('回到首页')
        }}
      </a-button>
    </template>
  </a-result>
</template>
<script lang="ts" setup>
import router from '@/router'

const handleClick = () => {
  router.push('/home')
}
</script>

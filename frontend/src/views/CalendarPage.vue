<template>
  <div class="wrap">
    <store-list />
    <calendar />
  </div>
</template>

<script>
import Calendar from '../components/Calendar';
import StoreList from '@/components/StoreList';
export default {
  components: {
    Calendar,
    StoreList,
  },
};
</script>
<style scoped>
.wrap {
  display: flex;
}
</style>

<template>
  <div class="wrap">
    <StoreList @select="select"/>
    <calendar :selected="selected" />
  </div>
</template>

<script>
import Calendar from '../components/Calendar';
import StoreList from '@/components/StoreList';
export default {
  name:'CalendarPage',
  components: {
    Calendar,
    StoreList,
  },
  data () {
    return {
      selected: []
    }
  },
  methods: {
    select (selected) {
      console.log(222)
      this.selected = selected
    }
  }
};
</script>
<style scoped>
.wrap {
  display: flex;
}
</style>

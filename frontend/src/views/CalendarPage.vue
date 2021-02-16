<template>
  <v-app>
    <StoreTag @select="select" />
    <div class="justify-center">
      <calendar :selected="selected" />
    </div>
    <div class="wrap">
      <!-- <StoreList @select="select" /> -->
    </div>
  </v-app>
</template>

<script>
import Calendar from '../components/Calendar';
// import StoreList from '@/components/StoreList';
import StoreTag from '@/components/StoreTag';
export default {
  name: 'CalendarPage',
  components: {
    Calendar,
    // StoreList,
    StoreTag,
  },
  data() {
    return {
      selected: [],
    };
  },
  methods: {
    select(selected) {
      this.selected = selected;
    },
  },
};
</script>
<style scoped>
.wrap {
  display: flex;
}
</style>

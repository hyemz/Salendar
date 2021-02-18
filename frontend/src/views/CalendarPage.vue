<template>
  <v-app>
    <v-container class="mt-12">
      <v-row no-gutters>
        <v-col cols="12" sm="1"></v-col>
        <v-col cols="12" sm="10">
          <div class="mb-10">
            <v-card flat class="mt-12" id="calendarmargin">
              <div class="titlefont text-center">
                관심 있는 매장을 <strong>찜</strong>하시면, <br />관련된
                <strong>세일 일정</strong>을 확인하실 수 있어요 😀
              </div>
            </v-card>
            <v-divider class="mt-12 mb-9"></v-divider>
          </div>
          <StoreTag @select="select" />
          <div class="justify-center">
            <calendar :selected="selected" />
          </div> </v-col></v-row
    ></v-container>
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
#calendarmargin.mt-12 {
  margin-top: 150px !important;
}
</style>

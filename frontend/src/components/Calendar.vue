<template>
  <div class="demo-app">
    <div class="demo-app-main">
      <FullCalendar class="demo-app-calendar" :options="calendarOptions" />
      <MoreModal :dialog="dialog" :focus="focus" @close="close" />
    </div>
  </div>
</template>

<script>
import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { mapState } from 'vuex';
import MoreModal from './MoreModal';
import axios from 'axios';

export default {
  components: {
    FullCalendar, // make the <FullCalendar> tag available
    MoreModal,
  },
  props: {
    selected: Array,
  },
  computed: {
    ...mapState(['following']),
  },
  data: function() {
    return {
      dialog: false,
      focus: null,
      followingEvents: [],
      stores:{
        Oliveyoung: {
          name: '올리브영 🥑',
          color: '#BDEDD1',
        },
        Lalavla: {
          name: '랄라블라 💗',
          color: '#FFCFDC',
        },

        Innisfree: {
          name: '이니스프리 🌿',
          color: '#f7f8fb',
        },
        Thefaceshop: {
          name: '더 페이스샵 👩',
          color: '#DFC6FF',
        },
        Etude: {
          name: '에뛰드 하우스 👑',
          color: '#CFE4FF',
        },
        Missha: {
          name: '미샤 💋',
          color: '#FBEC8F',
        },
        Aritaum: {
          name: '아리따움 🎀',
          color: '#FFDABD',
        },
        Tonymoly: {
          name: '토니모리 🐼',
          color: '#CCD1D1',
        },
      },
      calendarOptions: {
        events: [],
        plugins: [
          dayGridPlugin,
          timeGridPlugin,
          interactionPlugin, // needed for dateClick
        ],
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: '',
        },
        initialView: 'dayGridMonth',

        dayMaxEvents: true,
        weekends: true,
        select: this.handleDateSelect,
        locale: 'ko',
        eventClick: this.showMore,
        eventsSet: this.handleEvents,

      },
      currentEvents: [],
    };
  },
  created() {
    this.getSale()

  },
  methods: {
    showMore(clickInfo) {
      this.dialog = true;
      this.focus = clickInfo.event;
    },
    getSale(){
      // 팔로우 한 세일정보 가져오기
      const headers = {
            "x-auth-token": localStorage.getItem("jwt"),
        };
      const baseURL = "http://localhost:8080";
      axios
      .create({
          baseURL,
          headers,
      })
      .get('/api/sale/token/list/follow')
      .then((res) => {
        this.calendarOptions.events = []
        const now = res.data
        Object.keys(now).forEach(store => {
          now[store].forEach(el => {
            let start = new Date(el.sale_start_date)
            let end = new Date(el.sale_end_date)

            this.calendarOptions.events.push({
            title: this.stores[store].name + ' ' + ' '+ el.sale_title,
            start: new Date(start.setDate(start.getDate() + 1)),
            end: new Date(end.setDate(end.getDate() + 2)),
            bigImg: el.sale_big_img,
            thumbnail: el.sale_thumbnail,
            saleLink: el.sale_link,
            storeName: this.stores[store].name,
            saleTitle: el.sale_title,
            saleDsc: el.sale_dsc,
            allDay: true,
            color: this.stores[store].color,
            textColor: '#50555C',
            })
          })
        })
      })
      .catch((err) => {
        console.log("팔로우세일 로드에 실패했습니다.", err);
      });
    },

    close(isDialog) {
      this.dialog = isDialog;
    },
  },
  watch: {
    selected: function () {
      this.getSale()
    }
  }
};
</script>

<style lang="css">
h2 {
  margin: 0;
  font-size: 16px;
}

ul {
  margin: 0;
  padding: 0 0 0 1.5em;
}

li {
  margin: 1.5em 0;
  padding: 0;
}

b {
  /* used for event dates/times */
  margin-right: 3px;
}

.demo-app {
  display: flex;
  min-width: 50%;
  min-height: 100%;
  min-width: 50%;
  font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
  font-size: 14px;
}

.demo-app-sidebar {
  width: 300px;
  line-height: 1.5;
  background: #eaf9ff;
  border-right: 1px solid #d3e2e8;
}

.demo-app-sidebar-section {
  padding: 2em;
}

.demo-app-main {
  flex-grow: 1;
  padding: 3em;
}

.fc {
  /* the calendar root */
  max-width: 1100px;
  margin: 0 auto;
}

.fc .fc-toolbar-title {
    font-size: 1.75em;
    margin-right: 9rem;
}

.fc .fc-col-header-cell-cushion {
  color: black;
}

.fc .fc-daygrid-day-number {
  color: black;
}
</style>

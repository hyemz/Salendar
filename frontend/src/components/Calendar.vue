<template>
  <div class="demo-app">
    <div class="demo-app-main mb-12 mt-12">
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
      stores: {
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
          left: 'prev',
          center: 'title',
          right: 'next',
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
    this.getSale();
  },
  methods: {
    showMore(clickInfo) {
      this.dialog = true;
      this.focus = clickInfo.event;
    },
    getSale() {
      // 팔로우 한 세일정보 가져오기
      const headers = {
        'x-auth-token': localStorage.getItem('jwt'),
      };
      const baseURL = 'http://i4a301.p.ssafy.io:8080';
      axios
        .create({
          baseURL,
          headers,
        })
        .get('/api/sale/token/list/follow')
        .then((res) => {
          this.calendarOptions.events = [];
          const now = res.data;
          Object.keys(now).forEach((store) => {
            now[store].forEach((el) => {
              let start = new Date(el.sale_start_date);
              let end = new Date(el.sale_end_date);

              this.calendarOptions.events.push({
                title: this.stores[store].name + ' ' + ' ' + el.sale_title,
                start: new Date(start.setDate(start.getDate())),
                end: new Date(end.setDate(end.getDate() + 1)),
                bigImg: el.sale_big_img,
                thumbnail: el.sale_thumbnail,
                saleLink: el.sale_link,
                storeName: this.stores[store].name,
                saleTitle: el.sale_title,
                saleDsc: el.sale_dsc,
                allDay: true,
                color: this.stores[store].color,
                textColor: '#50555C',
              });
            });
          });
        })
        .catch((err) => {
          console.log('팔로우세일 로드에 실패했습니다.', err);
        });
    },

    close(isDialog) {
      this.dialog = isDialog;
    },
  },
  watch: {
    selected: function() {
      this.getSale();
    },
  },
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

.fc .fc-daygrid-more-link {
  color: #01579b;
}

.demo-app {
  display: flex;
  min-width: 50%;
  min-height: 100%;
  min-width: 50%;
  font-family: 'NanumBarunGothic';
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

.fc .fc-button:hover {
  background-color: rgb(248, 248, 248);
  border: none;
  cursor: pointer;
  border-radius: 5em;
}
.fc .fc-button:focus {
  background-color: transparent;
  border: none;
  color: #2c3e50;
  cursor: pointer;
  box-shadow: 0 0 0 0rem rgba(0, 0, 106, 0.5);
}
.fc .fc-button-primary:not(:disabled):active,
.fc .fc-button-primary:not(:disabled).fc-button-active {
  color: #2c3e50;
  color: var(--fc-button-text-color, #2c3e50);
  background-color: transparent;
  background-color: var(--fc-button-active-bg-color, transparent);
  border-color: transparent;
  border-color: var(--fc-button-active-border-color, transparent);
}
.fc .fc-button-primary:not(:disabled):active:focus,
.fc .fc-button-primary:not(:disabled).fc-button-active:focus {
  box-shadow: 0 0 0 0rem rgba(0, 0, 106, 0.5);
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
  font-size: 2.3em;
  font-family: 'S-CoreDream-3Light';
  color: #2c3e50;
}

.fc .fc-col-header-cell-cushion {
  color: #2c3e50;
  font-family: 'NanumBarunGothic';
}

.fc .fc-daygrid-day-number {
  color: #2c3e50;
  font-family: 'NanumBarunGothic';
}
.fc .fc-button-primary {
  content: '';
  background-color: transparent;
  border-color: transparent;
  color: #2c3e50;
}
.fc .fc-button:after {
  content: '';
  background-color: transparent;
  border-color: transparent;
  color: transparent;
}
.fc .fc-icon {
  background-color: transparent;
  border-color: transparent;
  color: #2c3e50;
}

@font-face {
  font-family: 'S-CoreDream-3Light';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'NanumBarunGothic';
  font-style: normal;
  font-weight: 1000;
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot');
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot?#iefix')
      format('embedded-opentype'),
    url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.woff') format('woff'),
    url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.ttf') format('truetype');
}
</style>

package com.example.emovieapp.Classes;

import java.io.Serializable;
import java.util.List;

public class ActorMoviesClass implements Serializable {

    private List<?> movie_results;
    private List<PersonResultsBean> person_results;
    private List<?> tv_results;
    private List<?> tv_episode_results;
    private List<?> tv_season_results;

    public List<?> getMovie_results() {
        return movie_results;
    }

    public void setMovie_results(List<?> movie_results) {
        this.movie_results = movie_results;
    }

    public List<PersonResultsBean> getPerson_results() {
        return person_results;
    }

    public void setPerson_results(List<PersonResultsBean> person_results) {
        this.person_results = person_results;
    }

    public List<?> getTv_results() {
        return tv_results;
    }

    public void setTv_results(List<?> tv_results) {
        this.tv_results = tv_results;
    }

    public List<?> getTv_episode_results() {
        return tv_episode_results;
    }

    public void setTv_episode_results(List<?> tv_episode_results) {
        this.tv_episode_results = tv_episode_results;
    }

    public List<?> getTv_season_results() {
        return tv_season_results;
    }

    public void setTv_season_results(List<?> tv_season_results) {
        this.tv_season_results = tv_season_results;
    }

    public static class PersonResultsBean implements Serializable{

        private boolean adult;
        private int gender;
        private String name;
        private int id;
        private String known_for_department;
        private String profile_path;
        private double popularity;
        private List<KnownForBean> known_for;

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKnown_for_department() {
            return known_for_department;
        }

        public void setKnown_for_department(String known_for_department) {
            this.known_for_department = known_for_department;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public List<KnownForBean> getKnown_for() {
            return known_for;
        }

        public void setKnown_for(List<KnownForBean> known_for) {
            this.known_for = known_for;
        }

        public static class KnownForBean implements Serializable{


            private int id;
            private boolean video;
            private int vote_count;
            private double vote_average;
            private String title;
            private String release_date;
            private String original_language;
            private String original_title;
            private String backdrop_path;
            private boolean adult;
            private String overview;
            private String poster_path;
            private double popularity;
            private String media_type;
            private List<Integer> genre_ids;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isVideo() {
                return video;
            }

            public void setVideo(boolean video) {
                this.video = video;
            }

            public int getVote_count() {
                return vote_count;
            }

            public void setVote_count(int vote_count) {
                this.vote_count = vote_count;
            }

            public double getVote_average() {
                return vote_average;
            }

            public void setVote_average(double vote_average) {
                this.vote_average = vote_average;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRelease_date() {
                return release_date;
            }

            public void setRelease_date(String release_date) {
                this.release_date = release_date;
            }

            public String getOriginal_language() {
                return original_language;
            }

            public void setOriginal_language(String original_language) {
                this.original_language = original_language;
            }

            public String getOriginal_title() {
                return original_title;
            }

            public void setOriginal_title(String original_title) {
                this.original_title = original_title;
            }

            public String getBackdrop_path() {
                return backdrop_path;
            }

            public void setBackdrop_path(String backdrop_path) {
                this.backdrop_path = backdrop_path;
            }

            public boolean isAdult() {
                return adult;
            }

            public void setAdult(boolean adult) {
                this.adult = adult;
            }

            public String getOverview() {
                return overview;
            }

            public void setOverview(String overview) {
                this.overview = overview;
            }

            public String getPoster_path() {
                return poster_path;
            }

            public void setPoster_path(String poster_path) {
                this.poster_path = poster_path;
            }

            public double getPopularity() {
                return popularity;
            }

            public void setPopularity(double popularity) {
                this.popularity = popularity;
            }

            public String getMedia_type() {
                return media_type;
            }

            public void setMedia_type(String media_type) {
                this.media_type = media_type;
            }

            public List<Integer> getGenre_ids() {
                return genre_ids;
            }

            public void setGenre_ids(List<Integer> genre_ids) {
                this.genre_ids = genre_ids;
            }
        }
    }
}

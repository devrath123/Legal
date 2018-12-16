package com.example.devrathrathee.legal.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CaseBean {

    List<CasesToday> cases_tomorrow;
    List<CasesToday> cases_today;
    List<CasesToday> cases_weekly;
    List<CasesToday> case_all;

    public List<CasesToday> getCase_all() {
        return case_all;
    }

    public void setCase_all(List<CasesToday> case_all) {
        this.case_all = case_all;
    }

    public List<CasesToday> getCases_weekly() {
        return cases_weekly;
    }

    public void setCases_weekly(List<CasesToday> cases_weekly) {
        this.cases_weekly = cases_weekly;
    }

    public List<CasesToday> getCases_tomorrow() {
        return cases_tomorrow;
    }

    public void setCases_tomorrow(List<CasesToday> cases_tomorrow) {
        this.cases_tomorrow = cases_tomorrow;
    }

    public List<CasesToday> getCases_today() {
        return cases_today;
    }

    public void setCases_today(List<CasesToday> cases_today) {
        this.cases_today = cases_today;
    }

    public static class CasesToday implements Parcelable{
        String case_id,display_prev_date,display_next_date,court_name,court_number,case_number,judge_name,party_a,party_b,
                stage,client_name,category,firm_name,client_phone,name;
        List<PrevCaseDate> pre_date;
        List<NextLawyerName> next_lawyer_name;
        List<NextUpdateDate> next_date;
        List<NextStage> next_stage;
        List<NextJudgeName> next_judge_name;

        protected CasesToday(Parcel in) {
            case_id = in.readString();
            display_prev_date = in.readString();
            display_next_date = in.readString();
            court_name = in.readString();
            court_number = in.readString();
            case_number = in.readString();
            judge_name = in.readString();
            party_a = in.readString();
            party_b = in.readString();
            stage = in.readString();
            client_name = in.readString();
            category = in.readString();
            firm_name = in.readString();
            client_phone = in.readString();
            name = in.readString();
            pre_date = in.createTypedArrayList(PrevCaseDate.CREATOR);
            next_lawyer_name = in.createTypedArrayList(NextLawyerName.CREATOR);
            next_date = in.createTypedArrayList(NextUpdateDate.CREATOR);
            next_stage = in.createTypedArrayList(NextStage.CREATOR);
            next_judge_name = in.createTypedArrayList(NextJudgeName.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(case_id);
            dest.writeString(display_prev_date);
            dest.writeString(display_next_date);
            dest.writeString(court_name);
            dest.writeString(court_number);
            dest.writeString(case_number);
            dest.writeString(judge_name);
            dest.writeString(party_a);
            dest.writeString(party_b);
            dest.writeString(stage);
            dest.writeString(client_name);
            dest.writeString(category);
            dest.writeString(firm_name);
            dest.writeString(client_phone);
            dest.writeString(name);
            dest.writeTypedList(pre_date);
            dest.writeTypedList(next_lawyer_name);
            dest.writeTypedList(next_date);
            dest.writeTypedList(next_stage);
            dest.writeTypedList(next_judge_name);
        }

        public List<PrevCaseDate> getPre_date() {
            return pre_date;
        }

        public void setPre_date(List<PrevCaseDate> pre_date) {
            this.pre_date = pre_date;
        }

        public List<NextLawyerName> getNext_lawyer_name() {
            return next_lawyer_name;
        }

        public void setNext_lawyer_name(List<NextLawyerName> next_lawyer_name) {
            this.next_lawyer_name = next_lawyer_name;
        }

        public List<NextUpdateDate> getNext_date() {
            return next_date;
        }

        public void setNext_date(List<NextUpdateDate> next_date) {
            this.next_date = next_date;
        }

        public List<NextJudgeName> getNext_judge_name() {
            return next_judge_name;
        }

        public void setNext_judge_name(List<NextJudgeName> next_judge_name) {
            this.next_judge_name = next_judge_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CasesToday> CREATOR = new Creator<CasesToday>() {
            @Override
            public CasesToday createFromParcel(Parcel in) {
                return new CasesToday(in);
            }

            @Override
            public CasesToday[] newArray(int size) {
                return new CasesToday[size];
            }
        };

        public String getCase_id() {
            return case_id;
        }

        public void setCase_id(String case_id) {
            this.case_id = case_id;
        }

        public String getDisplay_prev_date() {
            return display_prev_date;
        }

        public void setDisplay_prev_date(String display_prev_date) {
            this.display_prev_date = display_prev_date;
        }

        public String getDisplay_next_date() {
            return display_next_date;
        }

        public void setDisplay_next_date(String display_next_date) {
            this.display_next_date = display_next_date;
        }

        public String getCourt_name() {
            return court_name;
        }

        public void setCourt_name(String court_name) {
            this.court_name = court_name;
        }

        public String getCourt_number() {
            return court_number;
        }

        public void setCourt_number(String court_number) {
            this.court_number = court_number;
        }

        public String getCase_number() {
            return case_number;
        }

        public void setCase_number(String case_number) {
            this.case_number = case_number;
        }

        public String getJudge_name() {
            return judge_name;
        }

        public void setJudge_name(String judge_name) {
            this.judge_name = judge_name;
        }

        public String getParty_a() {
            return party_a;
        }

        public void setParty_a(String party_a) {
            this.party_a = party_a;
        }

        public String getParty_b() {
            return party_b;
        }

        public void setParty_b(String party_b) {
            this.party_b = party_b;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getFirm_name() {
            return firm_name;
        }

        public void setFirm_name(String firm_name) {
            this.firm_name = firm_name;
        }

        public String getClient_phone() {
            return client_phone;
        }

        public void setClient_phone(String client_phone) {
            this.client_phone = client_phone;
        }

        public List<PrevCaseDate> getPrev_case_date() {
            return pre_date;
        }

        public void setPrev_case_date(List<PrevCaseDate> prev_case_date) {
            this.pre_date = prev_case_date;
        }

        public List<NextLawyerName> getNextdt_id() {
            return next_lawyer_name;
        }

        public void setNextdt_id(List<NextLawyerName> nextdt_id) {
            this.next_lawyer_name = nextdt_id;
        }

        public List<NextUpdateDate> getNext_update_date() {
            return next_date;
        }

        public void setNext_update_date(List<NextUpdateDate> next_update_date) {
            this.next_date = next_update_date;
        }

        public List<NextStage> getNext_stage() {
            return next_stage;
        }

        public void setNext_stage(List<NextStage> next_stage) {
            this.next_stage = next_stage;
        }

        public static class PrevCaseDate implements Parcelable{
            String date;

            protected PrevCaseDate(Parcel in) {
                date = in.readString();
            }

            public static final Creator<PrevCaseDate> CREATOR = new Creator<PrevCaseDate>() {
                @Override
                public PrevCaseDate createFromParcel(Parcel in) {
                    return new PrevCaseDate(in);
                }

                @Override
                public PrevCaseDate[] newArray(int size) {
                    return new PrevCaseDate[size];
                }
            };

            public String getPrev_date() {
                return date;
            }

            public void setPrev_date(String prev_date) {
                this.date = prev_date;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(date);
            }
        }

        public static class NextLawyerName implements Parcelable{
            String name;

            protected NextLawyerName(Parcel in) {
                name = in.readString();
            }

            public static final Creator<NextLawyerName> CREATOR = new Creator<NextLawyerName>() {
                @Override
                public NextLawyerName createFromParcel(Parcel in) {
                    return new NextLawyerName(in);
                }

                @Override
                public NextLawyerName[] newArray(int size) {
                    return new NextLawyerName[size];
                }
            };

            public String getNext_id() {
                return name;
            }

            public void setNext_id(String next_id) {
                this.name = next_id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(name);
            }
        }

        public static class NextJudgeName implements Parcelable{
            String name;

            protected NextJudgeName(Parcel in) {
                name = in.readString();
            }

            public static final Creator<NextJudgeName> CREATOR = new Creator<NextJudgeName>() {
                @Override
                public NextJudgeName createFromParcel(Parcel in) {
                    return new NextJudgeName(in);
                }

                @Override
                public NextJudgeName[] newArray(int size) {
                    return new NextJudgeName[size];
                }
            };

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(name);
            }
        }

        public static class NextUpdateDate implements Parcelable{
            String date;

            protected NextUpdateDate(Parcel in) {
                date = in.readString();
            }

            public static final Creator<NextUpdateDate> CREATOR = new Creator<NextUpdateDate>() {
                @Override
                public NextUpdateDate createFromParcel(Parcel in) {
                    return new NextUpdateDate(in);
                }

                @Override
                public NextUpdateDate[] newArray(int size) {
                    return new NextUpdateDate[size];
                }
            };

            public String getNext_date() {
                return date;
            }

            public void setNext_date(String next_date) {
                this.date = next_date;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(date);
            }
        }

        public static class NextStage implements Parcelable{
            String stage;

            protected NextStage(Parcel in) {
                stage = in.readString();
            }

            public static final Creator<NextStage> CREATOR = new Creator<NextStage>() {
                @Override
                public NextStage createFromParcel(Parcel in) {
                    return new NextStage(in);
                }

                @Override
                public NextStage[] newArray(int size) {
                    return new NextStage[size];
                }
            };

            public String getStages() {
                return stage;
            }

            public void setStages(String stages) {
                this.stage = stages;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(stage);
            }
        }

    }

}

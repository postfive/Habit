package com.postfive.habit.habits;

import com.postfive.habit.habits.factory.Habit;
import com.postfive.habit.habits.factory.HabitFactory;
import com.postfive.habit.habits.storage.DrinkWaterHabit;
import com.postfive.habit.habits.storage.PreStudyHabit;
import com.postfive.habit.habits.storage.SkipRopeHabit;
import com.postfive.habit.habits.storage.UserSetHabit;

public class HabitMaker extends HabitFactory {
    @Override
    public Habit createHabit(int habitId) {

        Habit result = null;
/*        if (habitId == null){
            return null;
        }

        if(habit.equals("drinkwater")){
            return new DrinkWaterHabit();
        }else if (habit.equals("prestudy")){
            return new PreStudyHabit();
        }else if (habit.equals("skiprope")){
            return new SkipRopeHabit();
        }*/

//        switch (habitId){
//            case 1:
//                return new DrinkWaterHabit();
//
//            case 2:
//                return new SkipRopeHabit();
//            case 3 :
//                return new PreStudyHabit();
//            default :
//                break;
//        }
        if(habitId == 1){
            result = new DrinkWaterHabit();
        }else if (habitId == 2){
            result = new SkipRopeHabit();
        }else if (habitId == 3){
            result = new PreStudyHabit();
        }else if (habitId == 0){
            result = new UserSetHabit();
        }

        return result;
    }


    @Override
    public Habit createHabit(String habitType) {
        if (habitType == null){
            return null;
        }

        if(habitType.equals("drinkwater")){
            return new DrinkWaterHabit();
        }else if (habitType.equals("prestudy")){
            return new PreStudyHabit();
        }else if (habitType.equals("skiprope")){
            return new SkipRopeHabit();
        }else if (habitType.equals("userset")){
            return new UserSetHabit();
        }
        return null;
    }


    public Class getHabit(String habitType) {
        if (habitType == null){
            return null;
        }

        if(habitType.equals("drinkwater")){
            return DrinkWaterHabit.class;
        }else if (habitType.equals("prestudy")){
            return PreStudyHabit.class;
        }else if (habitType.equals("skiprope")){
            return SkipRopeHabit.class;
        }else if (habitType.equals("userset")){
            return UserSetHabit.class;
        }
        return null;
    }

    class EmptyHabit extends Habit{
        @Override
        public void prepare() {

        }
    }

}

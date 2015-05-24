package fi.fynes.mycalculator;

public class CalculationValue {

    private double lastSavedValue;
    private String lastSavedAction;

    public CalculationValue(){
        setLastSavedValue(0);
        setLastSavedAction("=");
    }

    public double getLastSavedValue() {
        return lastSavedValue;
    }

    public void setLastSavedValue(double lastSavedValue) {
        this.lastSavedValue = lastSavedValue;
    }

    public String getLastSavedAction() {
        return lastSavedAction;
    }

    public void setLastSavedAction(String lastSavedAction) {
        this.lastSavedAction = lastSavedAction;
    }


    public void runCalc(double value){

        if(lastSavedValue == 0){
            setLastSavedValue(value);
        }
        else {
            switch (lastSavedAction) {
                case "+":

                    setLastSavedValue(lastSavedValue + value);

                    break;

                case "-":
                    setLastSavedValue(lastSavedValue - value);
                    break;

                case "*":


                    setLastSavedValue(lastSavedValue * value);
                    break;

                case "/":

                    setLastSavedValue(lastSavedValue / value);

                    break;


            }
        }


    }

}

package commands;
import basic.*;
import javax.naming.InvalidNameException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class FieldsWork {

        public String name(InputStream sIn, PrintStream sOut)  {
            sOut.println("Введите Имя:");
            String name;
            while (true) {
                Scanner sc1 = new Scanner(System.in);
                name = sc1.nextLine();
                try {
                    if (name.isEmpty()) {
                        throw new InvalidNameException();
                    }
                    if (!name.matches("[a-zA-Zа-яА-ЯёЁ]+")) { // Проверяем, что имя состоит только из букв
                        throw new InvalidNameException();
                    }
                    break;
                } catch (InvalidNameException e) {
                    sOut.println("Некорректный ввод, попробуйте еще раз!");
                }
            }
            return name;
        }

        public Coordinates coordinates(InputStream sIn, PrintStream sOut) throws NullException {
            while(true) {
                try {
                    sOut.println("Введите координату по Х:");
                    Scanner sc2 = new Scanner(System.in);
                    String xInput = sc2.nextLine();
                    float x = Float.parseFloat(xInput);
                    if (xInput.isEmpty()) {
                        throw new NullException("Неверный ввод, попробуйте еще раз");
                    }
                    if (x >= 947) {
                        throw new IllegalArgumentException("Неверный ввод, X не более 946");
                    }

                    sOut.println("Введите координату по Y:");
                    Scanner sc3 = new Scanner(System.in);
                    String yInput = sc3.nextLine();
                    float y = Float.parseFloat(yInput);
                    if (yInput.isEmpty()) {
                        throw new NullException("Неверный ввод, попробуйте еще раз");
                    }

                    return new Coordinates(x, y);
                } catch (NumberFormatException e) {
                    sOut.println("Укажите данные корректно:");
                } catch (NullException exc) {
                    sOut.println("Ошибка. Попробуйте ещё раз");
                }catch(IllegalArgumentException e){
                    sOut.println("Неверный ввод, X не более 946");
                }
            }
        }


        public Float height(InputStream sIn, PrintStream sOut)  {
            sOut.println("Введите рост в метрах:");
            while(true) {
                Scanner sc4 = new Scanner(System.in);
                try {
                    String height = sc4.nextLine();
                    float h = Float.parseFloat(height);
                    if(h>=4){ throw new NumberFormatException();}
                    if (height.isEmpty()) {
                        throw new NullException("Вы ничего не ввели. Попробуйте ещё раз");
                    }
                    return h;
                } catch (NumberFormatException e) {
                    sOut.println("Некорректное значение. Повторите попытку");
                } catch (NullException exc) {
                    sOut.println("Ошибка. Попробуйте снова");
                }
            }
        }

        public String passport(InputStream sIn, PrintStream sOut) throws NullException {
            sOut.println("Введите серию и номера паспорта без пробелов:");
            while(true) {
                Scanner sc5 = new Scanner(System.in);
                String passport = sc5.nextLine();
                try {
                    long h = Long.parseLong(passport);
                    if(h <= 0 || h >= 999999999){throw new NumberFormatException();}
                    if (passport.isEmpty()) {
                        throw new NullException("Вы ничего не ввели, попробуйте еще раз.");
                    }
                    return passport;
                } catch (NumberFormatException exc) {
                    sOut.println("Вы ввели некорректные данные(");
                } catch (NullException ex) {
                    sOut.println("Вы ничего не ввели. Попробуйте снова");
                }
            }
        }

        public Color color(InputStream sIn, PrintStream sOut) throws IllegalArgumentException {
            while(true) {
                try {
                    sOut.println("Введите цвет волос, наиболее близкий к одному из перечисленных:\n RED, BLUE, YELLOW, WHITE");
                    Scanner sc6 = new Scanner(System.in);
                    Color color = Color.valueOf(sc6.nextLine().toUpperCase());
                    if ((color == Color.BLUE) || (color == Color.YELLOW) || (color == Color.RED) || (color == Color.WHITE)) {
                        return color;
                    }else{
                    throw new IllegalArgumentException();}
                }catch(IllegalArgumentException e){
                    sOut.println("Вы должны выбрать из перечисленных");
                }
            }
        }

        public Country country(InputStream sIn, PrintStream sOut) throws IllegalArgumentException {
            while(true) {
                try {
                    sOut.println("Введите страну проживания:\n USA, GERMANY, SPAIN, CHINA");
                    Scanner sc7 = new Scanner(System.in);
                    Country country = Country.valueOf(sc7.nextLine().toUpperCase());
                    if ((country == Country.CHINA) || (country == Country.USA) || (country == Country.GERMANY) || (country == Country.SPAIN)) {
                        return country;
                    }else{
                        throw new IllegalArgumentException();}
                }catch(IllegalArgumentException e){
                    sOut.println("Вы должны выбрать из перечисленных");
                }
            }

        }

        public Location location(InputStream sIn, PrintStream sOut) throws NullException {
            while (true) {
                try {
                    sOut.println("Введите Расположение:");
                    sOut.println("По Х");
                    Scanner sc8 = new Scanner(System.in);
                    String xInput = sc8.nextLine();
                    Float x = Float.parseFloat(xInput);
                    if (xInput.isEmpty()) {
                        throw new NullException("Неверный ввод, попробуйте еще раз");
                    }

                    sOut.println("По Y");
                    Scanner sc9 = new Scanner(System.in);
                    String yInput = sc9.nextLine();
                    float y = Float.parseFloat(yInput);
                    if (yInput.isEmpty()) {
                        throw new NullException("Неверный ввод, попробуйте еще раз");
                    }

                    sOut.println("По Z");
                    Scanner sc10 = new Scanner(System.in);
                    String zInput = sc10.nextLine();
                    Float z = Float.parseFloat(zInput);
                    if (zInput.isEmpty()) {
                        throw new NullException("Неверный ввод, попробуйте еще раз");
                    }

                    return new Location(x, y, z);
                } catch (NumberFormatException exc) {
                    sOut.println("Вы ввели некорректные данные(");
                } catch (NullException ex) {
                    sOut.println("Вы ничего не ввели. Попробуйте снова");
                }
            }
        }

    public long id(InputStream sIn, PrintStream sOut) throws NullException {
        sOut.println("Введите ID:");
        while(true) {
            Scanner sc11 = new Scanner(System.in);
            String id = sc11.nextLine();
            try {

                if (id.isEmpty()) {
                    throw new NullException("Вы ничего не ввели, попробуйте еще раз.");
                }
                long h = Long.parseLong(id);
                if(h <= 0){throw new NumberFormatException();}
                return h;
            } catch (NumberFormatException exc) {
                System.out.println("Вы ввели некорректные данные(");
            } catch (NullException ex) {
                System.out.println("Вы ничего не ввели. Попробуйте снова");
            }
        }
    }
}



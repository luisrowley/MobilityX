package com.mobilityX.models.vehicle;

public class Moto extends Vehiculo {
    private final int cilindrada;

    public Moto(String id, int bateria, int x, int y, int cilindrada) {
        super(id, bateria, x, y);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    @Override
    public String toString() {
        return "Moto{" +
                "id='" + getId() + '\'' +
                ", batería=" + getBateria() + "%" +
                ", ubicación=(" + getX() + ", " + getY() + ")" +
                ", cilindrada=" + cilindrada +
                '}';
    }

    public static class MotoPequena extends Moto {
        private static final int CILINDRADA = 50;

        public MotoPequena(String id, int bateria, int x, int y) {
            super(id, bateria, x, y, CILINDRADA);
        }
    }

    public static class MotoGrande extends Moto {
        private static final int CILINDRADA = 125;

        public MotoGrande(String id, int bateria, int x, int y) {
            super(id, bateria, x, y, CILINDRADA);
        }
    }
}

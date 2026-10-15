public class ComplexNumber {
    private final double real;
    private final double imag;

    public ComplexNumber(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public ComplexNumber(double real) {
        this(real, 0);
    }

    public double getReal() { return real; }
    public double getImag() { return imag; }

    public ComplexNumber plus(ComplexNumber b) {
        return new ComplexNumber(this.real + b.real, this.imag + b.imag);
    }

    public ComplexNumber minus(ComplexNumber b) {
        return new ComplexNumber(this.real - b.real, this.imag - b.imag);
    }

    public ComplexNumber times(ComplexNumber b) {
        double r = this.real * b.real - this.imag * b.imag;
        double i = this.real * b.imag + this.imag * b.real;
        return new ComplexNumber(r, i);
    }

    public ComplexNumber div(ComplexNumber b) {
        double den = b.real * b.real + b.imag * b.imag;
        if (den == 0) {
            throw new ArithmeticException("На ноль делить нельзя!");
        }
        ComplexNumber conj = new ComplexNumber(b.real, -b.imag);
        ComplexNumber num = this.times(conj);
        return new ComplexNumber(num.real / den, num.imag / den);
    }

    public double module() {
        return Math.sqrt(real * real + imag * imag);
    }

    public boolean isNull() {
        return real == 0 && imag == 0;
    }

    @Override
    public String toString() {
        if (imag == 0) return String.format("%.2f", real);
        if (real == 0) return String.format("%.2fi", imag);
        if (imag < 0) return String.format("%.2f - %.2fi", real, -imag);
        return String.format("%.2f + %.2fi", real, imag);
    }
}
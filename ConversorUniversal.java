import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class ConversorUniversal extends JFrame {
    private JComboBox<String> tipoConversaoComboBox;
    private JComboBox<String> unidadeOrigemComboBox;
    private JComboBox<String> unidadeDestinoComboBox;
    private JTextField valorOrigemTextField;
    private JLabel resultadoLabel;
    private JButton converterButton;

    // Taxa de câmbio fictícia (para exemplo)
    private final double TAXA_DOLAR = 5.20;
    private final double TAXA_EURO = 5.60;
    private final double TAXA_LIBRA = 6.40;

    public ConversorUniversal() {
        setTitle("Conversor Universal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Componentes da interface
        add(new JLabel("Tipo de Conversão:"));
        String[] tiposConversao = { "Temperatura", "Moeda", "Peso" };
        tipoConversaoComboBox = new JComboBox<>(tiposConversao);
        add(tipoConversaoComboBox);

        add(new JLabel("De:"));
        unidadeOrigemComboBox = new JComboBox<>();
        add(unidadeOrigemComboBox);

        add(new JLabel("Para:"));
        unidadeDestinoComboBox = new JComboBox<>();
        add(unidadeDestinoComboBox);

        add(new JLabel("Valor:"));
        valorOrigemTextField = new JTextField();
        add(valorOrigemTextField);

        converterButton = new JButton("Converter");
        add(converterButton);

        resultadoLabel = new JLabel("Resultado: ");
        add(resultadoLabel);

        // Atualiza as unidades quando o tipo de conversão muda
        tipoConversaoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarUnidades();
            }
        });

        // Configura o botão de conversão
        converterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                converter();
            }
        });

        // Inicializa as unidades
        atualizarUnidades();
    }

    private void atualizarUnidades() {
        String tipo = (String) tipoConversaoComboBox.getSelectedItem();
        unidadeOrigemComboBox.removeAllItems();
        unidadeDestinoComboBox.removeAllItems();

        if (tipo.equals("Temperatura")) {
            unidadeOrigemComboBox.addItem("Celsius");
            unidadeOrigemComboBox.addItem("Fahrenheit");
            unidadeOrigemComboBox.addItem("Kelvin");

            unidadeDestinoComboBox.addItem("Celsius");
            unidadeDestinoComboBox.addItem("Fahrenheit");
            unidadeDestinoComboBox.addItem("Kelvin");
        } else if (tipo.equals("Moeda")) {
            unidadeOrigemComboBox.addItem("Real (BRL)");
            unidadeOrigemComboBox.addItem("Dólar (USD)");
            unidadeOrigemComboBox.addItem("Euro (EUR)");
            unidadeOrigemComboBox.addItem("Libra (GBP)");

            unidadeDestinoComboBox.addItem("Real (BRL)");
            unidadeDestinoComboBox.addItem("Dólar (USD)");
            unidadeDestinoComboBox.addItem("Euro (EUR)");
            unidadeDestinoComboBox.addItem("Libra (GBP)");
        } else if (tipo.equals("Peso")) {
            unidadeOrigemComboBox.addItem("Quilograma (kg)");
            unidadeOrigemComboBox.addItem("Grama (g)");
            unidadeOrigemComboBox.addItem("Miligrama (mg)");
            unidadeOrigemComboBox.addItem("Libra (lb)");
            unidadeOrigemComboBox.addItem("Onça (oz)");

            unidadeDestinoComboBox.addItem("Quilograma (kg)");
            unidadeDestinoComboBox.addItem("Grama (g)");
            unidadeDestinoComboBox.addItem("Miligrama (mg)");
            unidadeDestinoComboBox.addItem("Libra (lb)");
            unidadeDestinoComboBox.addItem("Onça (oz)");
        }
    }

    private void converter() {
        try {
            double valor = Double.parseDouble(valorOrigemTextField.getText());
            String unidadeOrigem = (String) unidadeOrigemComboBox.getSelectedItem();
            String unidadeDestino = (String) unidadeDestinoComboBox.getSelectedItem();
            String tipo = (String) tipoConversaoComboBox.getSelectedItem();

            double resultado = 0;

            if (tipo.equals("Temperatura")) {
                resultado = converterTemperatura(valor, unidadeOrigem, unidadeDestino);
            } else if (tipo.equals("Moeda")) {
                resultado = converterMoeda(valor, unidadeOrigem, unidadeDestino);
            } else if (tipo.equals("Peso")) {
                resultado = converterPeso(valor, unidadeOrigem, unidadeDestino);
            }

            DecimalFormat df = new DecimalFormat("#,##0.00");
            resultadoLabel.setText("Resultado: " + df.format(resultado) + " " + unidadeDestino);
        } catch (NumberFormatException ex) {
            resultadoLabel.setText("Por favor, insira um valor válido.");
        }
    }

    private double converterTemperatura(double valor, String de, String para) {
        // Converter para Celsius primeiro
        double celsius;
        switch (de) {
            case "Celsius":
                celsius = valor;
                break;
            case "Fahrenheit":
                celsius = (valor - 32) * 5 / 9;
                break;
            case "Kelvin":
                celsius = valor - 273.15;
                break;
            default:
                celsius = 0;
        }

        // Converter de Celsius para unidade de destino
        switch (para) {
            case "Celsius":
                return celsius;
            case "Fahrenheit":
                return celsius * 9 / 5 + 32;
            case "Kelvin":
                return celsius + 273.15;
            default:
                return 0;
        }
    }

    private double converterMoeda(double valor, String de, String para) {
        // Converter para Real primeiro
        double reais;
        switch (de) {
            case "Real (BRL)":
                reais = valor;
                break;
            case "Dólar (USD)":
                reais = valor * TAXA_DOLAR;
                break;
            case "Euro (EUR)":
                reais = valor * TAXA_EURO;
                break;
            case "Libra (GBP)":
                reais = valor * TAXA_LIBRA;
                break;
            default:
                reais = 0;
        }

        // Converter de Real para moeda de destino
        switch (para) {
            case "Real (BRL)":
                return reais;
            case "Dólar (USD)":
                return reais / TAXA_DOLAR;
            case "Euro (EUR)":
                return reais / TAXA_EURO;
            case "Libra (GBP)":
                return reais / TAXA_LIBRA;
            default:
                return 0;
        }
    }

    private double converterPeso(double valor, String de, String para) {
        // Converter para gramas primeiro
        double gramas;
        switch (de) {
            case "Quilograma (kg)":
                gramas = valor * 1000;
                break;
            case "Grama (g)":
                gramas = valor;
                break;
            case "Miligrama (mg)":
                gramas = valor / 1000;
                break;
            case "Libra (lb)":
                gramas = valor * 453.592;
                break;
            case "Onça (oz)":
                gramas = valor * 28.3495;
                break;
            default:
                gramas = 0;
        }

        // Converter de gramas para unidade de destino
        switch (para) {
            case "Quilograma (kg)":
                return gramas / 1000;
            case "Grama (g)":
                return gramas;
            case "Miligrama (mg)":
                return gramas * 1000;
            case "Libra (lb)":
                return gramas / 453.592;
            case "Onça (oz)":
                return gramas / 28.3495;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConversorUniversal().setVisible(true);
            }
        });
    }
}
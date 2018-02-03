

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MessageBuilder {


    public MessageBuilder(CRC_16 crc) {
        this.crc = crc;
    }

    private CRC_16 crc;

    /**
     * Builds a Message to read Holdingregisters.
     * @param address
     * @param from
     * @param length
     * @return
     */
    public  byte[] readAHoldingRegister(byte address, byte from, byte length) {
        ArrayList<Byte> message = new ArrayList<Byte>();
        message.add(address);//address

        message.add((byte) 3);

        //start address
        message.add((byte) 0);
        message.add((byte) from);

        //Quantity address
        message.add((byte) 0);
        message.add( length);


        byte[] erg = new byte[message.size()];
        for (int i = 0; i < message.size(); i++) {
            erg[i] = (byte) message.get(i);
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt(crc.getcrc(erg));
        message.add(byteBuffer.array()[3]);                                    //CRC Lo
        message.add(byteBuffer.array()[2]);

        erg = new byte[message.size()];
        for (int i = 0; i < message.size(); i++) {
            erg[i] =  message.get(i);
        }

        return erg;
    }

    /**
     * Writes one Register
     * @param address
     * @param register
     * @param data
     * @return
     */
    public byte[] writeInputRegister(byte address, HoldingRegister register, short data) {
        ArrayList<Byte> message = new ArrayList<Byte>();

        message.add(address);                                   //address
        message.add((byte)0x10);   //Function

        message.add((byte) 0);                                  //start Register Hi
        message.add(register.value);                            //start Register Lo

        message.add((byte) 0);                                  //Quantity Hi
        message.add((byte) 1);                                  //Quantity Lo

        message.add((byte) 2);                                  //Byte Count

        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(data);

        message.add(buffer.array()[0]);                         //Register Value Hi
        message.add(buffer.array()[1]);                         //Register Value Lo
        byte[] erg = new byte[message.size()];
        for (int i = 0; i < message.size(); i++) {
            erg[i] = message.get(i);
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt(crc.getcrc(erg));
        message.add(byteBuffer.array()[3]);                                    //CRC Lo
        message.add(byteBuffer.array()[2]);                                    //CRC Hi

        erg = new byte[message.size()];
        for (int i = 0; i < message.size(); i++) {
            erg[i] = message.get(i);
        }
        return erg;

    }

}

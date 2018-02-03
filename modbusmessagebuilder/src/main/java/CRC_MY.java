/**
 * Created by Jannik Upmann on 25.12.2017.
 *
 * This Class is a Static Member to calculate the CRC-16 out of a input Array
 * <h1>Use only getcrc(byte[] input)!</h1>
 */

public class CRC_MY implements CRC_16 {


    /**
     * Use this Method to clculate CRC Checksum
     * @param input input array
     * @return Integer CRC value of Input
     */
    public int getcrc(byte[] input) {
        char crc_sum_MBM = 0xffff;
        char in;
        for (byte b : input) {

            if(b<0)
               in = (char) (b&0xFF);
            else
                in = (char) b;
            crc_sum_MBM = add_crc_MBM(in,crc_sum_MBM);
        }
        return crc_sum_MBM;
    }

    /**
     * Method to help calculate the CRC Checksum
     * @param current_byte
     * @param crc_sum_MBM
     * @return actual sum of state
     */
    private static char add_crc_MBM(char current_byte, char crc_sum_MBM) {

        char idx;
        crc_sum_MBM ^= current_byte;

        for (idx = 8; idx != 0; idx--) {    // Loop over each bit
            if ((crc_sum_MBM & 0x0001) != 0) {      // If the LSB is set
                crc_sum_MBM >>= 1;                    // Shift right and XOR 0xA001
                crc_sum_MBM ^= 0xA001;
            } else                            // Else LSB is not set
                crc_sum_MBM >>= 1;                    // Just shift right
        }
        return crc_sum_MBM;


    }


}
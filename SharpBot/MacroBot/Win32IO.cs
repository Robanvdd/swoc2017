using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace MacroBot
{
    public class Win32IO : IDisposable
    {
        private const uint STD_INPUT_HANDLE = 0xfffffff6;
        private const uint STD_OUTPUT_HANDLE = 0xfffffff5;
        private const uint STD_ERROR_HANDLE = 0xfffffff4;
        private const uint ATTACH_PARENT_PROCESS = 0xffffffff;

        [DllImport("kernel32.dll", SetLastError = true)]
        public static extern bool AttachConsole(uint dwProcessId);
        [DllImport("kernel32.dll", SetLastError = true)]
        public static extern bool AllocConsole();
        [DllImport("kernel32.dll", SetLastError = true)]
        public static extern bool FreeConsole();
        [DllImport("kernel32.dll", SetLastError = true)]
        public static extern int GetStdHandle(uint nStdHandle);
        [DllImport("kernel32.dll", SetLastError = true)]
        public static extern bool WriteConsole(int hConsoleOutput, string lpBuffer, int nNumberOfCharsToWrite, ref int lpNumberOfCharsWritten, int lpReserved);
        [DllImport("kernel32.dll", SetLastError = true)]
        public static extern bool ReadConsole(int hConsoleInput, StringBuilder lpBuffer, int nNumberOfCharsToRead, ref int lpNumberOfCharsRead, int lpReserved);

        [DllImport("kernel32.dll")]
        public static extern bool SetConsoleMode(int hConsoleHandle, uint dwMode);

        [Flags]
        private enum ConsoleInputModes : uint
        {
            ENABLE_PROCESSED_INPUT = 0x0001,
            ENABLE_LINE_INPUT = 0x0002,
            ENABLE_ECHO_INPUT = 0x0004,
            ENABLE_WINDOW_INPUT = 0x0008,
            ENABLE_MOUSE_INPUT = 0x0010,
            ENABLE_INSERT_MODE = 0x0020,
            ENABLE_QUICK_EDIT_MODE = 0x0040,
            ENABLE_EXTENDED_FLAGS = 0x0080,
            ENABLE_AUTO_POSITION = 0x0100,
        }

        [Flags]
        private enum ConsoleOutputModes : uint
        {
            ENABLE_PROCESSED_OUTPUT = 0x0001,
            ENABLE_WRAP_AT_EOL_OUTPUT = 0x0002,
            ENABLE_VIRTUAL_TERMINAL_PROCESSING = 0x0004,
            DISABLE_NEWLINE_AUTO_RETURN = 0x0008,
            ENABLE_LVB_GRID_WORLDWIDE = 0x0010,
        }

        private int stdin;
        private int stdout;

        public Win32IO()
        {
            //if (!AllocConsole())
            //    System.Windows.Forms.MessageBox.Show("Alloc console failed");
            //if (!AttachConsole(ATTACH_PARENT_PROCES`S))
            //    System.Windows.Forms.MessageBox.Show("Attach parent process failed");
            stdin = GetStdHandle(STD_INPUT_HANDLE);
            stdout = GetStdHandle(STD_OUTPUT_HANDLE);
            if (stdin == -1 || stdout == -1)
                throw new Exception("in/out handles failure");
        }

        public void WriteLine(string s)
        {
            int len = 0;
            var success = WriteConsole(stdout, s + "\r\n", s.Length + 2, ref len, 0);
            if (!success)
                System.Windows.Forms.MessageBox.Show(Marshal.GetLastWin32Error().ToString());
        }

        public string ReadLine()
        {
            int len = 0;
            int buffSize = (int)Math.Pow(2, 10);
            StringBuilder sb = new StringBuilder(buffSize);
            if (!ReadConsole(stdin, sb, buffSize, ref len, 0))
                return "";
            else
                return sb.ToString(0, Math.Max(0, len - 2));
        }

        public void Dispose()
        {
            //FreeConsole();
        }
    }
}
